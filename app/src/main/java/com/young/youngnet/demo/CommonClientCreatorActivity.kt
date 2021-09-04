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
import com.young.youngnet.R
import com.young.youngnet.databinding.ActivityCommonClientCreatorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/9/3 10:06 周五
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

 * @description: 公共的基础的请求客户端构建者 demo
 */
class CommonClientCreatorActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityCommonClientCreatorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_common_client_creator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get -> {
                get()
            }
            R.id.getSync -> {
                getSync()
            }
            R.id.delete -> {
                delete()
            }
            R.id.deleteSync -> {
                deleteSync()
            }
            R.id.options -> {
                options()
            }
            R.id.optionsSync -> {
                optionsSync()
            }
            R.id.head -> {
                head()
            }
            R.id.headSync -> {
                headSync()
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
     * get 请求
     */
    fun get() {
        showLoading()
        YoungNetWorking.createCommonClientCreator("user", Any::class.java)
            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
            .addHeader("agent","android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .get(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult(e.msg)
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult(data?.toString())
                    hideLoading()
                }
            })
    }

    /**
     * get 同步请求
     */
    fun getSync() {
        showLoading()
        Thread {
            try {
                val result = YoungNetWorking.createCommonClientCreator("user", Any::class.java)
                    .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
                    .addHeader("agent","android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .get()
                showResult("getSync ${result?.toString()}")
            } catch (e: Exception) {
                e.printStackTrace()
                showResult("getSync ${e.message}")
            } finally {
                hideLoading()
            }
        }.start()
    }


    /**
     * delete 请求
     */
    fun delete() {
        showLoading()
        YoungNetWorking.createCommonClientCreator("user", Any::class.java)
            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
            .addHeader("agent","android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .delete(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult(e.msg)
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult(data?.toString())
                    hideLoading()
                }
            })
    }

    /**
     * delete 同步请求
     */
    fun deleteSync() {
        showLoading()
        Thread {
            try {
                val result = YoungNetWorking.createCommonClientCreator("user", Any::class.java)
                    .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
                    .addHeader("agent","android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .delete()
                showResult("deleteSync ${result?.toString()}")
            } catch (e: Exception) {
                e.printStackTrace()
                showResult("deleteSync ${e.message}")
            } finally {
                hideLoading()
            }
        }.start()
    }

    /**
     * options 请求
     */
    fun options() {
        showLoading()
        YoungNetWorking.createCommonClientCreator("user", Any::class.java)
            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
            .addHeader("agent","android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .options(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult("options ${e.msg}")
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult("options ${data?.toString()}")
                    hideLoading()
                }
            })
    }

    /**
     * options 同步请求
     */
    fun optionsSync() {
        showLoading()
        Thread {
            try {
                val result = YoungNetWorking.createCommonClientCreator("user", Any::class.java)
                    .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
                    .addHeader("agent","android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .options()
                showResult("optionsSync ${result?.toString()}")
            } catch (e: Exception) {
                e.printStackTrace()
                showResult("optionsSync ${e.message}")
            } finally {
                hideLoading()
            }
        }.start()
    }

    /**
     * head 请求
     */
    fun head() {
        showLoading()
        YoungNetWorking.createCommonClientCreator("user", Any::class.java)
            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
            .addHeader("agent","android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .head(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    showResult("options ${t.localizedMessage}")
                    hideLoading()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    showResult("options ${response.body()?.toString()}")
                    hideLoading()
                }
            })
    }

    /**
     * head 同步请求
     */
    fun headSync() {
        showLoading()
        Thread {
            try {
                val result = YoungNetWorking.createCommonClientCreator("user", Any::class.java)
                    .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
                    .addHeader("agent","android-app")
                    .setGetCall(object : IGetCall {
                        override fun onGet(call: Call<*>) {
                            Log.e("shenlong", "call call call ${call}")
                        }

                    })
                    .build()
                    .head()
                showResult("headSync ${result?.body()?.toString()}")
            } catch (e: Exception) {
                e.printStackTrace()
                showResult("headSync ${e.message}")
            } finally {
                hideLoading()
            }
        }.start()
    }
}