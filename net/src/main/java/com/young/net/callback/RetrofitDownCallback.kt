package com.young.net.callback

import android.os.Handler
import com.young.net.NetInit
import com.young.net.constant.ErrorCode
import com.young.net.exception.ApiException
import com.young.net.message.DownProgressInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 18:11 周六
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

 * @description: retrofit 下载回调
 *
 * android Retrofit下载图片
 * https://blog.csdn.net/u010326875/article/details/72778691
 */
class RetrofitDownCallback(
    private val downUrl: String,
    private val file: File,
    // 进度回调
    private val handler: Handler,
    // 获取 Call<?> 对象的回调
    private val callCallback: IGetCall?
) : Callback<ResponseBody> {

    companion object {
        // 上传成功
        const val MSG_TYPE_SUCCESS = 0x10

        // 上传失败
        const val MSG_TYPE_FAILURE = 0x11

        // 上传中
        const val MSG_TYPE_PROGRESS = 0x13
    }

    private val mDownProgressInfo by lazy {
        DownProgressInfo(downUrl, file.absolutePath)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        callCallback?.onGet(call)
        if (response.isSuccessful) {
            val body = response.body()
            try {
                if (body == null) {
                    throw ApiException(ErrorCode.DOWNLOAD_EMPTY, "下载源错误")
                }
                val contentLength = body.contentLength()

                if (contentLength == 0L) {
                    throw ApiException(ErrorCode.DOWNLOAD_EMPTY, "下载源错误")
                }

                val byteStream = body.byteStream()
                if (onIO(byteStream, contentLength)) {
                    handler.obtainMessage(MSG_TYPE_SUCCESS, mDownProgressInfo)
                        .sendToTarget()
                }
            } catch (e: Exception) {
                //e.printStackTrace()
                onFailure(ApiException.handleException(e))
            }
        } else {
            var message = response.errorBody()?.toString()
            if (message == null || message.isEmpty()) {
                message = response.message()
            }
            onFailure(ApiException(response.code(), message))
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        onFailure(ApiException.handleException(t))
    }

    private fun onFailure(e: ApiException) {
        mDownProgressInfo.e = e
        handler.obtainMessage(MSG_TYPE_FAILURE, mDownProgressInfo)
            .sendToTarget()
        handler.post {
            NetInit.commonErrorCallback?.onCall(e)
        }
    }

    /**
     * 进行 io 操作
     *
     * [byteStream] 字节流
     *
     * [contentLength] 内容总长度
     *
     */
    private fun onIO(byteStream: InputStream, contentLength: Long): Boolean {
        var success = false
        var fos: FileOutputStream? = null
        var bis: BufferedInputStream? = null
        try {
            fos = FileOutputStream(file)
            bis = BufferedInputStream(byteStream)

            val buffer = ByteArray(1024)
            var len: Int

            var progress: Long = 0

            while ((bis.read(buffer).also { len = it }) != -1) {
                fos.write(buffer, 0, len)
                progress += len

                mDownProgressInfo.progress = progress
                mDownProgressInfo.total = contentLength
                handler.obtainMessage(MSG_TYPE_PROGRESS, mDownProgressInfo)
                    .sendToTarget()
            }
            fos.flush()
            success = true
        } catch (e: Exception) {
            //e.printStackTrace()
            onFailure(ApiException.handleException(e))
        } finally {
            try {
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                bis?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                byteStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return success
    }
}