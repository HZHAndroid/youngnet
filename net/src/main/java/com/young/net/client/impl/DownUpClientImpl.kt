package com.young.net.client.impl

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.young.net.callback.ICallback
import com.young.net.body.ProgressRequestBody
import com.young.net.callback.*
import com.young.net.client.DownUpClient
import com.young.net.client.base.BaseClient
import com.young.net.message.DownProgressInfo
import com.young.net.message.UploadProgressInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.File
import java.lang.reflect.Type


/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 17:18 周六
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

 * @description: 上传/下载请求客户端
 */
class DownUpClientImpl<T>(
    url: String,
    resultType: Type,
    headerMap: Map<String, String>,
    paramMap: MutableMap<String, Any>?
) : BaseClient<T>(url, resultType, headerMap, paramMap), DownUpClient<T> {

    private fun getDownloadCall(): Call<ResponseBody> {
        return getDownloadCall(url)
    }

    private fun getDownloadCall(url: String): Call<ResponseBody> {
        return getDownUpApi().download(url, paramMap ?: mutableMapOf(), headerMap)
    }


    override fun download(file: File, callBack: IDownloadCallback) {
        val downloadHandle = getDownloadHandle(1, callBack)
        getDownloadCall()
            .enqueue(RetrofitDownCallback(url, file, downloadHandle))
    }


    override fun downloadSync(file: File, callBack: IDownloadCallback) {
        val downloadHandle = getDownloadHandle(1, callBack)
        downloadSync(url, file, downloadHandle)
    }

    override fun download(filePath: String, callBack: IDownloadCallback) {
        download(File(filePath), callBack)
    }


    override fun downloadSync(filePath: String, callBack: IDownloadCallback) {
        downloadSync(File(filePath), callBack)
    }

    // 调用当前方法，链接上的 url 可以传递空
    override fun downloads(files: Map<String, String>, callBack: IDownloadCallback) {
        val downloadHandle = getDownloadHandle(files.size, callBack)
        Thread {
            // 下载路径
            val downUrlSet = files.keys

            for (downUrl in downUrlSet) {
                // 保存路径
                val savePath = files[downUrl]!!
                downloadSync(downUrl, File(savePath), downloadHandle)
            }
        }.start()
    }

    override fun downloadsSync(files: Map<String, String>, callBack: IDownloadCallback) {
        val downloadHandle = getDownloadHandle(files.size, callBack)

        // 下载路径
        val downUrlSet = files.keys

        for (downUrl in downUrlSet) {
            // 保存路径
            val savePath = files[downUrl]!!
            downloadSync(downUrl, File(savePath), downloadHandle)

        }
    }

    override fun upload(
        fileKey: String,
        file: File,
        uploadCallback: IUploadCallback,
        callback: ICallback<T>
    ) {
        execute(
            getDownUpApi().upload(
                url,
                getUploadBody(fileKey, file, uploadCallback),
                headerMap
            ), callback
        )
    }

    override fun uploadSync(fileKey: String, file: File, uploadCallback: IUploadCallback): T? {
        return execute(
            getDownUpApi().upload(
                url,
                getUploadBody(fileKey, file, uploadCallback),
                headerMap
            )
        )
    }

    override fun uploads(
        fileKey: String,
        files: List<File>,
        uploadCallback: IUploadCallback,
        callback: ICallback<T>
    ) {

        execute(
            getDownUpApi().upload(
                url,
                getUploadBody(fileKey, files, uploadCallback),
                headerMap
            ), callback
        )
    }

    override fun uploadsSync(
        fileKey: String,
        files: List<File>,
        uploadCallback: IUploadCallback
    ): T? {
        return execute(
            getDownUpApi().upload(
                url,
                getUploadBody(fileKey, files, uploadCallback),
                headerMap
            )
        )
    }

    /**
     * 获取上传请求体
     */
    private fun getUploadBody(
        fileKey: String,
        file: File,
        uploadCallback: IUploadCallback
    ): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        paramMap?.let {
            if (it.isNotEmpty()) {
                for (mutableEntry in it) {
                    builder.addFormDataPart(mutableEntry.key, mutableEntry.value.toString())
                }
            }
        }

        val uploadHandler = getUploadHandler(1, uploadCallback)
        builder.addFormDataPart(
            fileKey,
            file.name,
            ProgressRequestBody(file, uploadHandler)
        )

        return builder.build()
    }

    /**
     * 获取上传请求体
     */
    private fun getUploadBody(
        fileKey: String,
        files: List<File>,
        uploadCallback: IUploadCallback
    ): RequestBody {
        // 参考：https://www.jianshu.com/p/acfefb0a204f
        // 多参数的形式
//                final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart("name", name)
//                        .addFormDataPart("psd", psd)
//                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//                        .build();
        // 如果是多张图片，只需要多次调用
        // .addFormDataPart("file", UPLOAD_FILE.getName(), RequestBody.create(UPLOAD_FILE, MediaType.parse("image/*"))) 即可
//                final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart("file", UPLOAD_FILE.getName(), RequestBody.create(UPLOAD_FILE, MediaType.parse("image/*")))
//                        .build();
//                call = service.upLoad(URL, requestBody);

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        paramMap?.let {
            if (it.isNotEmpty()) {
                for (mutableEntry in it) {
                    builder.addFormDataPart(mutableEntry.key, mutableEntry.value.toString())
                }
            }
        }

        if (files.isNotEmpty()) {

            val uploadHandler = getUploadHandler(files.size, uploadCallback)
            for (file in files) {
//                builder.addFormDataPart(
//                    fileKey,
//                    file.name,
//                    RequestBody.Companion.create(MediaType.parse("*/*"), file)
//                )
//                builder.addFormDataPart(
//                    fileKey,
//                    file.name,
//                    file.asRequestBody("*/*".toMediaTypeOrNull())
//                )

                builder.addFormDataPart(
                    fileKey,
                    file.name,
                    ProgressRequestBody(file, uploadHandler)
                )


//                builder.addPart(
//                    MultipartBody.Part.createFormData(
//                        fileKey,
//                        file.name,
//                        ProgressRequestBody(file, uploadHandler)
//                    )
//                )

            }
        }
        return builder.build()
    }

    /**
     * 同步下载的逻辑
     *
     * @param url 下载的 url
     *
     * @param file 下载的文件
     *
     * @param downloadHandle 下载进度监听
     */
    private fun downloadSync(url: String, file: File, downloadHandle: Handler) {
        var call: Call<ResponseBody>? = null
        val retrofitDownCallback = RetrofitDownCallback(url, file, downloadHandle)
        try {
            call = getDownloadCall(url)
            retrofitDownCallback.onResponse(call, call.execute())
        } catch (e: Exception) {
            //e.printStackTrace()
            if (call != null) {
                retrofitDownCallback.onFailure(call, e)
            }
        }
    }

    /**
     * 获取上传进度的控制 Handler
     *
     * [fileSize] 上传文件的个数
     *
     * [uploadCallback] 上传回调
     */
    private fun getUploadHandler(fileSize: Int, uploadCallback: IUploadCallback): Handler {
        return Handler(Looper.getMainLooper(), object : Handler.Callback {
            // 已经上传的文件的个数
            private var mProgress = 0

            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    ProgressRequestBody.MSG_TYPE_PROGRESS -> {
                        onProgress(msg.obj)
                        return true
                    }
                    ProgressRequestBody.MSG_TYPE_FAILURE -> {
                        onFailure(msg.obj)
                        return true
                    }
                    ProgressRequestBody.MSG_TYPE_SUCCESS -> {
                        onSuccess(msg.obj)
                        return true
                    }
                }
                return false
            }

            private fun onProgress(info: Any) {
                val uploadProgressInfo = info as UploadProgressInfo
                uploadCallback.onSubProgress(
                    uploadProgressInfo.filePath,
                    uploadProgressInfo.progress,
                    uploadProgressInfo.total
                )
            }

            private fun onSuccess(info: Any) {
                val uploadProgressInfo = info as UploadProgressInfo
                uploadCallback.onSuccess(uploadProgressInfo.filePath)
                uploadCallback.onProgress(uploadProgressInfo.filePath, ++mProgress, fileSize)
            }

            private fun onFailure(info: Any) {
                val uploadProgressInfo = info as UploadProgressInfo
                uploadCallback.onFailure(
                    uploadProgressInfo.filePath,
                    uploadProgressInfo.e!!
                )
                uploadCallback.onProgress(uploadProgressInfo.filePath, ++mProgress, fileSize)
            }
        })
    }

    /**
     * 获取下载的进度控制 Handler
     *
     * [fileSize] 需要下载文件的个数
     *
     * [callBack] 进度回调
     */
    private fun getDownloadHandle(fileSize: Int, callBack: IDownloadCallback): Handler {
        return Handler(Looper.getMainLooper(), object : Handler.Callback {

            // 已经下载的文件的个数
            private var mProgress = 0

            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    RetrofitDownCallback.MSG_TYPE_PROGRESS -> {
                        onProgress(msg.obj)
                        return true
                    }
                    RetrofitDownCallback.MSG_TYPE_FAILURE -> {
                        onFailure(msg.obj)
                        return true
                    }
                    RetrofitDownCallback.MSG_TYPE_SUCCESS -> {
                        onSuccess(msg.obj)
                        return true
                    }
                }
                return false
            }

            private fun getDownProgressInfo(info: Any): DownProgressInfo {
                return info as DownProgressInfo
            }

            private fun onProgress(info: Any) {
                val downProgressInfo = getDownProgressInfo(info)
                callBack.onSubProgress(
                    downProgressInfo.downUrl,
                    downProgressInfo.filePath,
                    downProgressInfo.progress,
                    downProgressInfo.total
                )
            }

            private fun onSuccess(info: Any) {
                val downProgressInfo = getDownProgressInfo(info)
                callBack.onSuccess(downProgressInfo.downUrl, downProgressInfo.filePath)
                callBack.onProgress(
                    downProgressInfo.downUrl,
                    downProgressInfo.filePath,
                    ++mProgress,
                    fileSize
                )
            }

            private fun onFailure(info: Any) {
                val downProgressInfo = getDownProgressInfo(info)
                callBack.onFailure(
                    downProgressInfo.downUrl,
                    downProgressInfo.filePath,
                    downProgressInfo.e!!
                )
                callBack.onProgress(
                    downProgressInfo.downUrl,
                    downProgressInfo.filePath,
                    ++mProgress,
                    fileSize
                )
            }
        })
    }
}