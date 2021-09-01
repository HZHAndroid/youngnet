package com.young.net.callback

import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 17:51 周六
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

 * @description: 下载回调
 */
interface IDownloadCallback {

    /**
     * 下载进度(每个文件的进度)
     *
     * [downUrl] 下载 url
     *
     * [filePath] 文件路径
     *
     * [progress] 当前已下载的内容长度
     *
     * [total] 总的内容长度
     */
    fun onSubProgress(downUrl: String, filePath: String, progress: Long, total: Long)

    /**
     * 下载进度(一个文件下载完成之后，就会调用这个方法)
     *
     *
     * [downUrl] 下载 url
     *
     * [filePath] 文件路径
     *
     * [progress] 当前已下载的内容长度
     *
     * [total] 总的内容长度
     */
    fun onProgress(downUrl: String, filePath: String, progress: Int, total: Int)

    /**
     * 下载失败(每一条下载失败的时候的回调)
     *
     * [downUrl] 下载 url
     *
     * [filePath] 文件路径
     *
     * [e] 异常信息
     */
    fun onFailure(downUrl: String, filePath: String, e: ApiException)

    /**
     * 下载成功(每一条下载失败的时候的回调)
     *
     * [downUrl] 下载 url
     *
     * [filePath] 文件路径
     */
    fun onSuccess(downUrl: String, filePath: String)


}