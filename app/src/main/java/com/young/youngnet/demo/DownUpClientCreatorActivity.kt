package com.young.youngnet.demo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.young.net.YoungNetWorking
import com.young.net.callback.ICallback
import com.young.net.callback.IDownloadCallback
import com.young.net.callback.IUploadCallback
import com.young.net.exception.ApiException
import com.young.youngnet.R
import com.young.youngnet.databinding.ActivityDownupClientCreatorBinding
import java.io.File
import java.util.*

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/9/3 10:10 周五
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

 * @description: 下载/上传客户端构建者 demo
 */
class DownUpClientCreatorActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityDownupClientCreatorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_downup_client_creator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.download -> {
                download()
            }
            R.id.downloadSync -> {
                downloadSync()
            }
            R.id.downloads -> {
                downloads()
            }
            R.id.downloadsSync -> {
                downloadsSync()
            }
            R.id.upload -> {
                upload()
            }
            R.id.uploadSync -> {
                uploadSync()
            }
            R.id.uploads -> {
                uploads()
            }
            R.id.uploadsSync -> {
                uploadsSync()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 显示进度条的提示 label
     * @param flag 1：下载 2：上传
     */
    private fun showProgressLabel(flag: Int) {
        var prefix = ""

        when (flag) {
            1 -> prefix = "下载"
            2 -> prefix = "上传"
        }
        mBinding.progressBar2.progress = 0
        mBinding.progressBar3.progress = 0
        mBinding.tvSubProgress.text = "$prefix 子进度"
        mBinding.tvProgress.text = "$prefix 总进度"
    }

    private val list = mutableListOf<AppCompatImageView>()

    /**
     * 添加图片
     * [picPath] 文件路径
     */
    private fun addImage(picPath: String) {
        try {
            val imageView = AppCompatImageView(this)
            imageView.setImageBitmap(BitmapFactory.decodeFile(picPath))
            mBinding.llContainer.addView(imageView)
            list.add(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun removeImage() {
        try {
            if (list.isNotEmpty()) {
                for (appCompatImageView in list) {
                    mBinding.llContainer.removeView(appCompatImageView)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showSubProgress(progress: Int) {
        mBinding.progressBar2.progress = progress
    }

    private fun showTotalProgress(progress: Int, total: Int) {
        mBinding.progressBar3.max = total
        mBinding.progressBar3.progress = progress
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

    private fun showToast(msg: String?) {
        Toast.makeText(this, msg ?: "", Toast.LENGTH_LONG).show()
    }

    /**
     * 获取下载的回调
     * @param tab 标签。日志的标志
     */
    private fun getDownloadCallback(tab: String): IDownloadCallback {
        return object : IDownloadCallback {
            private val stringBuilder = StringBuilder()

            override fun onFailure(downUrl: String, filePath: String, e: ApiException) {
                stringBuilder.append("$tab：").append("failure: $downUrl : ${e.msg}")
                    .append("\n")
                showResult(stringBuilder.toString())
            }

            override fun onProgress(
                downUrl: String,
                filePath: String,
                progress: Int,
                total: Int
            ) {
                if (total != 0) {
                    showTotalProgress(progress, total)
                }
            }

            override fun onSubProgress(
                downUrl: String,
                filePath: String,
                progress: Long,
                total: Long
            ) {
                if (total != 0L) {
                    showSubProgress(((progress / total) * 100).toInt())
                }
            }

            override fun onSuccess(downUrl: String, filePath: String) {
                stringBuilder.append("$tab：").append("success: $downUrl ").append("\n")
                showResult(stringBuilder.toString())
                addImage(filePath)
            }

        }
    }

    /**
     * 单文件下载
     */
    private fun download() {

        removeImage()
        showResult("result")
        showProgressLabel(1)
        val file = File(cacheDir, "hello.jpg")
        YoungNetWorking.createDownUpClientCreator(
            "https://ae01.alicdn.com/kf/Uf8cd7a9ee0054a1c85f95633ccb722fc3.jpg",
            String::class.java
        )
            .addParam("hello", "hi")
            .addHeader(
                "shenlong",
                "tian"
            )
            .build()
            .download(file, getDownloadCallback("download"))

    }

    /**
     * 单文件下载（同步）
     */
    private fun downloadSync() {

        removeImage()
        showResult("result")
        showProgressLabel(1)
        Thread {
            val file = File(cacheDir, "hello.jpg")
            YoungNetWorking.createDownUpClientCreator(
                "https://ae01.alicdn.com/kf/Uf8cd7a9ee0054a1c85f95633ccb722fc3.jpg",
                String::class.java
            )
                .addParam("hello", "hi")
                .addHeader(
                    "shenlong",
                    "tian"
                )
                .build()
                .downloadSync(file, getDownloadCallback("downloadSync"))
        }.start()
    }

    /**
     * 多文件下载
     */
    private fun downloads() {
        removeImage()
        showResult("result")
        showProgressLabel(1)

        val map: MutableMap<String, String> = HashMap()
        map["https://ae01.alicdn.com/kf/Uf8cd7a9ee0054a1c85f95633ccb722fc3.jpg"] =
            File(externalCacheDir, "hello.jpg").absolutePath

        map["https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg"] =
            File(externalCacheDir, "hello1.jpg").absolutePath

        YoungNetWorking.createDownUpClientCreator("", Any::class.java)
            .addParam("hello", "hi")
            .addHeader("shenlong", "tian")
            .build()
            .downloads(map, getDownloadCallback("downloads"))
    }

    /**
     * 多文件下载（同步）
     */
    private fun downloadsSync() {
        removeImage()
        showResult("result")
        showProgressLabel(1)

        val map: MutableMap<String, String> = HashMap()
        map["https://ae01.alicdn.com/kf/Uf8cd7a9ee0054a1c85f95633ccb722fc3.jpg"] =
            File(externalCacheDir, "hello.jpg").absolutePath

        map["https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg"] =
            File(externalCacheDir, "hello1.jpg").absolutePath

        Thread {
            YoungNetWorking.createDownUpClientCreator("", Any::class.java)
                .addParam("hello", "hi")
                .addHeader("shenlong", "tian")
                .build()
                .downloadsSync(map, getDownloadCallback("downloadsSync"))
        }.start()
    }

    /**
     * 获取上传的回调
     */
    private fun getUploadCallback(stringBuffer: StringBuffer): IUploadCallback {
        return object : IUploadCallback {
            override fun onFailure(filePath: String, e: ApiException) {
                stringBuffer.append("failure: ").append(filePath).append(" ${e.msg}").append("\n")
            }

            override fun onProgress(filePath: String, progress: Int, total: Int) {
                if (total != 0) {
                    showTotalProgress(progress, total)
                }
            }

            override fun onSubProgress(filePath: String, progress: Long, total: Long) {
                if (total != 0L) {
                    showSubProgress(((progress / total) * 100).toInt())
                }
            }

            override fun onSuccess(filePath: String) {
                stringBuffer.append("success: ").append(filePath).append("\n")
            }
        }
    }

    /**
     * 单文件上传
     */
    private fun upload() {

        val file = File(cacheDir, "hello.jpg")
        if (!file.exists()) {
            showToast("请先进行单文件上传")
            return
        }

        removeImage()
        showResult("result")
        showProgressLabel(2)


        val stringBuffer = StringBuffer()
        stringBuffer.append("upload").append("\n")

        YoungNetWorking.createDownUpClientCreator(
            "https://imgbb.com/json",
            Any::class.java
        )
            .addParam("action", "upload")
            .addParam("auth_token", "232c8e0cdb5e960cfc2233b80bd8ad7345b17090")
            .addParam("timestamp", "1630168059700")
            .addParam("type", "file")
            .build()
            .upload("source", file, getUploadCallback(stringBuffer),
                object : ICallback<Any> {
                    override fun onFailure(e: ApiException) {
                        stringBuffer.append("onFailure = ${e.msg} ${e.message}")
                        showResult(stringBuffer.toString())
                    }

                    override fun onSuccess(data: Any?) {
                        stringBuffer.append("onSuccess = ${data}")
                        showResult(stringBuffer.toString())
                    }

                })
    }

    /**
     * 单文件上传（同步）
     */
    private fun uploadSync() {
        val file = File(cacheDir, "hello.jpg")
        if (!file.exists()) {
            showToast("请先进行单文件下载")
            return
        }

        removeImage()
        showResult("result")
        showProgressLabel(2)


        val stringBuffer = StringBuffer()
        stringBuffer.append("uploadSync").append("\n")

        Thread {
            try {
                val data = YoungNetWorking.createDownUpClientCreator(
                    "https://imgbb.com/json",
                    Any::class.java
                )
                    .addParam("action", "upload")
                    .addParam("auth_token", "232c8e0cdb5e960cfc2233b80bd8ad7345b17090")
                    .addParam("timestamp", "1630168059700")
                    .addParam("type", "file")
                    .build()
                    .uploadSync("source", file, getUploadCallback(stringBuffer))
                stringBuffer.append("onSuccess = ${data}")
                showResult(stringBuffer.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                stringBuffer.append("onFailure = ${e.message}")
                showResult(stringBuffer.toString())
            }
        }.start()
    }

    /**
     * 多文件上传
     */
    private fun uploads() {
        val file = File(externalCacheDir, "hello.jpg")
        val file1 = File(externalCacheDir, "hello1.jpg")
        if (!file.exists() || !file1.exists()) {
            showToast("请先进行多文件下载")
            return
        }

        val stringBuffer = StringBuffer()
        stringBuffer.append("uploads").append("\n")


        val files: MutableList<File> = ArrayList()
        files.add(file)
        files.add(file1)

        YoungNetWorking.createDownUpClientCreator(
            "https://imgbb.com/json",
            Any::class.java
        )
            .addParam("action", "upload")
            .addParam("auth_token", "232c8e0cdb5e960cfc2233b80bd8ad7345b17090")
            .addParam("timestamp", "1630168059700")
            .addParam("type", "file")
            .build()
            .uploads("source", files, getUploadCallback(stringBuffer), object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    stringBuffer.append("onFailure = ${e.msg} ${e.message}")
                    showResult(stringBuffer.toString())
                }

                override fun onSuccess(data: Any?) {
                    stringBuffer.append("onSuccess = ${data}")
                    showResult(stringBuffer.toString())
                }
            })
    }

    /**
     * 多文件上传（同步）
     */
    private fun uploadsSync() {
        val file = File(externalCacheDir, "hello.jpg")
        val file1 = File(externalCacheDir, "hello1.jpg")
        if (!file.exists() || !file1.exists()) {
            showToast("请先进行多文件下载")
            return
        }

        val stringBuffer = StringBuffer()
        stringBuffer.append("uploadsSync").append("\n")


        val files: MutableList<File> = ArrayList()
        files.add(file)
        files.add(file1)

        Thread {
            try {
                val data = YoungNetWorking.createDownUpClientCreator(
                    "https://imgbb.com/json",
                    Any::class.java
                )
                    .addParam("action", "upload")
                    .addParam("auth_token", "232c8e0cdb5e960cfc2233b80bd8ad7345b17090")
                    .addParam("timestamp", "1630168059700")
                    .addParam("type", "file")
                    .build()
                    .uploadsSync(
                        "source",
                        files,
                        getUploadCallback(stringBuffer)
                    )
                stringBuffer.append("onSuccess = ${data}")
                showResult(stringBuffer.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                stringBuffer.append("onFailure = ${e.message}")
                showResult(stringBuffer.toString())
            }
        }.start()
    }

}