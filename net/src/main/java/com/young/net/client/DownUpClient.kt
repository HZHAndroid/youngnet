package com.young.net.client

import com.young.net.callback.ICallback
import com.young.net.callback.IDownloadCallback
import com.young.net.callback.IUploadCallback
import com.young.net.exception.ApiException
import java.io.File

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 17:19 周六
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
interface DownUpClient<T> {

    /**
     * 下载
     *
     *
     * [file] 保存的文件路径
     *
     * [callBack] 下载的回调
     *
     */
    fun download(file: File, callBack: IDownloadCallback)

    /**
     * 下载（同步）
     *
     *
     * [file] 保存的文件路径
     *
     * [callBack] 下载的回调
     *
     */
    fun downloadSync(file: File, callBack: IDownloadCallback)

    /**
     * 下载
     *
     *
     * [filePath] 保存的文件路径
     *
     * [callBack] 下载的回调
     *
     */
    fun download(filePath: String, callBack: IDownloadCallback)


    /**
     * 下载（同步）
     *
     *
     * [filePath] 保存的文件路径
     *
     * [callBack] 下载的回调
     *
     */
    fun downloadSync(filePath: String, callBack: IDownloadCallback)


    /**
     * 批量下载文件
     *
     * [files] 下载链接和保存路径（key：下载链接  value: 保存路径）
     *
     * [callBack] 下载的回调
     */
    fun downloads(files: Map<String, String>, callBack: IDownloadCallback)

    /**
     * 批量下载文件（同步）
     *
     * [files] 下载链接和保存路径（key：下载链接  value: 保存路径）
     *
     * [callBack] 下载的回调
     */
    fun downloadsSync(files: Map<String, String>, callBack: IDownloadCallback)

    /**
     * 上传文件
     *
     * [fileKey] 文件 key，具体的值，是跟后端约定的，就看看后端想用什么字段接收
     *
     * [file] 需要上传的文件
     *
     * [uploadCallback] 上传进度监听
     *
     * [callback] 请求回调
     */
    fun upload(
        fileKey: String,
        file: File,
        uploadCallback: IUploadCallback,
        callback: ICallback<T>
    )

    /**
     * 上传文件（同步）
     *
     * [fileKey] 文件 key，具体的值，是跟后端约定的，就看看后端想用什么字段接收
     *
     * [file] 需要上传的文件
     *
     * [uploadCallback] 上传进度监听
     */
    @Throws(ApiException::class)
    fun uploadSync(
        fileKey: String,
        file: File,
        uploadCallback: IUploadCallback
    ): T?

    /**
     * 批量上传文件
     *
     * [fileKey] 文件 key，具体的值，是跟后端约定的，就看看后端想用什么字段接收
     *
     * [files] 需要上传的文件
     *
     * [uploadCallback] 上传进度监听
     *
     * [callback] 请求回调
     */
    fun uploads(
        fileKey: String,
        files: List<File>,
        uploadCallback: IUploadCallback,
        callback: ICallback<T>
    )

    /**
     * 批量上传文件
     *
     * [fileKey] 文件 key，具体的值，是跟后端约定的，就看看后端想用什么字段接收
     *
     * [files] 需要上传的文件
     *
     * [uploadCallback] 上传进度监听
     *
     * [callback] 请求回调
     */
    @Throws(ApiException::class)
    fun uploadsSync(
        fileKey: String,
        files: List<File>,
        uploadCallback: IUploadCallback
    ): T?
}