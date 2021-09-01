package com.young.net.callback

import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/29 10:45 周日
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

 * @description: 上传文件回调
 */
interface IUploadCallback {

    /**
     * 上传进度(文件进行 io 流的时候的进度)
     *
     * [filePath] 文件路径
     *
     * [progress] 当前已上传的内容长度
     *
     * [total] 总的内容长度
     */
    fun onSubProgress(filePath: String, progress: Long, total: Long)

    /**
     * 每个文件上传成功
     *
     * [filePath] 文件路径
     */
    fun onSuccess(filePath: String)

    /**
     * 每个文件上传失败
     *
     *
     * [filePath] 文件路径
     *
     * [e] 异常信息
     */
    fun onFailure(filePath: String, e: ApiException)

    /**
     * 上传进度（每上传完成一个，就会调用一次）
     *
     * [filePath] 文件路径
     *
     * [progress] 上传完成的文件个数
     *
     * [total] 上传的文件总个数
     *
     */
    fun onProgress(filePath: String, progress: Int, total: Int)
}