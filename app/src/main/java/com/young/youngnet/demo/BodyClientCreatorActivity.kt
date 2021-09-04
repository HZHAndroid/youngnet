package com.young.youngnet.demo

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.young.net.YoungNetWorking
import com.young.net.callback.ICallback
import com.young.net.callback.IGetCall
import com.young.net.exception.ApiException
import com.young.net.utils.JsonUtil
import com.young.youngnet.R
import com.young.youngnet.databinding.ActivityBodyClientCreatorBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/9/3 10:09 周五
 * 　　 へ　　　　　／|
　　/＼7　　∠＿/
　 /　│　　 ／　／
　│　Z ＿,＜　／　　 /`ヽ
　│　　　　　ヽ　　 /　　〉
　 Y　　　　　`　 /　　/
　ｲ●　､　●　　⊂⊃〈　　/
　()　 へ　　　　|　＼〈
　　>ｰ ､_　 ィ　 │ ／／
　 / へ　　 /　ﾉ＜| ＼＼
　 ヽ_ﾉ　　(_／　 │／／
　　7　　　　　　　|／
　　＞―r￣￣`ｰ―＿

 * @description: 构建带Body等功能的请求客户端构建者 demo
 */
class BodyClientCreatorActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityBodyClientCreatorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_body_client_creator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.post -> {
                post()
            }
            R.id.postSync -> {
                postSync()
            }
            R.id.put -> {
                put()
            }
            R.id.putSync -> {
                putSync()
            }
            R.id.patch -> {
                patch()
            }
            R.id.patchSync -> {
                pathSync()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showResult(msg: String?) {
        runOnUiThread {
            mBinding.textView.text = msg ?: ""
        }
    }

    private fun showLoading() {
        runOnUiThread {
            mBinding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        runOnUiThread {
            mBinding.progressBar.visibility = View.GONE
        }
    }

    /**
     * post 请求
     */
    private fun post() {
        showLoading()
        // 方式 1
//        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
//            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
//            .addParam("bookId", "${SystemClock.currentThreadTimeMillis()}")
//            .addHeader("agent","android-app")
//            .setGetCall(object : IGetCall {
//                override fun onGet(call: Call<*>) {
//                    Log.e("shenlong", "call call call ${call}")
//                }
//
//            })
//            .build()
//            .post(object : ICallback<Any> {
//                override fun onFailure(e: ApiException) {
//                    showResult("post ${e.msg}")
//                    hideLoading()
//                }
//
//                override fun onSuccess(data: Any?) {
//                    showResult("post ${data?.toString()}")
//                    hideLoading()
//                }
//            })

        // 方式 2
        val paramMap = mutableMapOf<String, Any>()
        paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
        paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
            .setBody(
                JsonUtil.toJson(paramMap)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            )
            .addHeader("agent", "android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .post(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult("post ${e.msg}")
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult("post ${data?.toString()}")
                    hideLoading()
                }
            })
    }

    /**
     * post 请求(同步)
     */
    private fun postSync() {
        Thread {

            try {
                val paramMap = mutableMapOf<String, Any>()
                paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
                paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

                val result = YoungNetWorking.createBodyClientCreator("book", Any::class.java)
                    .setBody(
                        JsonUtil.toJson(paramMap)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    )
                    .addHeader("agent", "android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .post()
                showResult("postSync ${result?.toString()}")

            } catch (e: Exception) {
                showResult("postSync ${e.message}")
            } finally {
                hideLoading()
            }

        }.start()
    }

    /**
     * put 请求
     */
    private fun put() {
        showLoading()
        // 方式 1
//        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
//            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
//            .addParam("bookId", "${SystemClock.currentThreadTimeMillis()}")
//            .addHeader("agent","android-app")
//            .setGetCall(object : IGetCall {
//                override fun onGet(call: Call<*>) {
//                    Log.e("shenlong", "call call call ${call}")
//                }
//
//            })
//            .build()
//            .put(object : ICallback<Any> {
//                override fun onFailure(e: ApiException) {
//                    showResult("put ${e.msg}")
//                    hideLoading()
//                }
//
//                override fun onSuccess(data: Any?) {
//                    showResult("put ${data?.toString()}")
//                    hideLoading()
//                }
//            })

        // 方式 2
        val paramMap = mutableMapOf<String, Any>()
        paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
        paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
            .setBody(
                JsonUtil.toJson(paramMap)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            )
            .addHeader("agent", "android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .put(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult("put ${e.msg}")
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult("put ${data?.toString()}")
                    hideLoading()
                }
            })
    }

    /**
     * put 请求(同步)
     */
    private fun putSync() {
        Thread {

            try {
                val paramMap = mutableMapOf<String, Any>()
                paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
                paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

                val result = YoungNetWorking.createBodyClientCreator("book", Any::class.java)
                    .setBody(
                        JsonUtil.toJson(paramMap)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    )
                    .addHeader("agent", "android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .put()
                showResult("putSync ${result?.toString()}")

            } catch (e: Exception) {
                showResult("putSync ${e.message}")
            } finally {
                hideLoading()
            }

        }.start()
    }

    /**
     * patch 请求
     */
    private fun patch() {
        showLoading()
        // 方式 1
//        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
//            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
//            .addParam("bookId", "${SystemClock.currentThreadTimeMillis()}")
//            .addHeader("agent","android-app")
//            .setGetCall(object : IGetCall {
//                override fun onGet(call: Call<*>) {
//                    Log.e("shenlong", "call call call ${call}")
//                }
//
//            })
//            .build()
//            .patch(object : ICallback<Any> {
//                override fun onFailure(e: ApiException) {
//                    showResult("patch ${e.msg}")
//                    hideLoading()
//                }
//
//                override fun onSuccess(data: Any?) {
//                    showResult("patch ${data?.toString()}")
//                    hideLoading()
//                }
//            })

        // 方式 2
        val paramMap = mutableMapOf<String, Any>()
        paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
        paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
            .setBody(
                JsonUtil.toJson(paramMap)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            )
            .addHeader("agent", "android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .patch(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult("patch ${e.msg}")
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult("patch ${data?.toString()}")
                    hideLoading()
                }
            })
    }

    /**
     * path 请求(同步)
     */
    private fun pathSync() {
        Thread {

            try {
                val paramMap = mutableMapOf<String, Any>()
                paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
                paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

                val result = YoungNetWorking.createBodyClientCreator("book", Any::class.java)
                    .setBody(
                        JsonUtil.toJson(paramMap)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    )
                    .addHeader("agent", "android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .patch()
                showResult("pathSync ${result?.toString()}")

            } catch (e: Exception) {
                showResult("pathSync ${e.message}")
            } finally {
                hideLoading()
            }

        }.start()
    }

}