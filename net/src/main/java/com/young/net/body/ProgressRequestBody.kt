package com.young.net.body

import android.os.Handler
import com.young.net.exception.ApiException
import com.young.net.message.UploadProgressInfo
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream


/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/29 0:35 周日
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

 * @description: 上传文件，进度监听
 *
 * Retrofit 多文件上传进度监听
 * https://blog.csdn.net/qq_17524035/article/details/80679271
 *
 * MediaType.parse(String contentType)中参数contentType常见的有：
text/html ： HTML格式
text/plain ：纯文本格式
text/xml ：  XML格式
text/x-markdown： markdown文档
image/gif ：gif图片格式
image/jpeg ：jpg图片格式
image/png：png图片格式

以application开头的媒体格式类型：
application/xhtml+xml ：XHTML格式
application/xml     ： XML数据格式
application/atom+xml  ：Atom XML聚合格式
application/json    ： JSON数据格式
application/pdf       ：pdf格式
application/msword  ： Word文档格式
application/octet-stream ： 二进制流数据（如常见的文件下载）
application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）

另外一种常见的媒体格式是上传文件之时使用的：
multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
 */
class ProgressRequestBody(
    // 需要上传的文件
    private val file: File,
    // 上传回调
    private val handler: Handler
) : RequestBody() {

    companion object {
        // 上传成功
        const val MSG_TYPE_SUCCESS = 0x10

        // 上传失败
        const val MSG_TYPE_FAILURE = 0x11

        // 上传中
        const val MSG_TYPE_PROGRESS = 0x13
    }

    // 缓冲区大小
    private val DEFAULT_BUFFER_SIZE = 2048


    // 更新的信息
    private val mUploadProgressInfo by lazy {
        UploadProgressInfo(file.absolutePath, total = contentLength())
    }

    override fun contentType(): MediaType? {
        // i want to upload only images
//        return MediaType.parse("image/*");
//        return "*/*".toMediaTypeOrNull();
        return "*/*".toMediaTypeOrNull();
    }

    override fun contentLength(): Long {
        return file.length()
    }

//    override fun isOneShot(): Boolean {
//        // https://blog.csdn.net/u013626215/article/details/107014153
//        // 此处返回true，防止添加了日志拦截器导致，
//        // 上传文件时writeTo函数2次调用（因为日志拦截器里面也调用了一次）
//        // 这里没有效果，主要还是网络日志级别的问题
//        return true
//    }

    override fun writeTo(sink: BufferedSink) {

        var fis: FileInputStream? = null
        try {
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            fis = FileInputStream(file)
            var uploaded: Long = 0

            var read: Int


            while ((fis.read(buffer).also { read = it }) != -1) {
                uploaded += read
                sink.write(buffer, 0, read)

                mUploadProgressInfo.progress = uploaded
                handler.obtainMessage(MSG_TYPE_PROGRESS, mUploadProgressInfo).sendToTarget()
            }
            sink.flush()
        } catch (e: Exception) {
            mUploadProgressInfo.e = ApiException.handleException(e)
            handler.obtainMessage(MSG_TYPE_FAILURE, mUploadProgressInfo).sendToTarget()
        } finally {
            try {
                fis?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            handler.obtainMessage(MSG_TYPE_SUCCESS, mUploadProgressInfo).sendToTarget()
        }
    }


}