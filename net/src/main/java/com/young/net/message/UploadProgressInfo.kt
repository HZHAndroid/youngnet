package com.young.net.message

import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/29 11:27 周日
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

 * @description: 上传进度信息
 */
data class UploadProgressInfo(
    // 上传的路径
    var filePath: String,
    // 上传进度
    var progress: Long = 0,
    // 总的文件大小
    var total: Long = 0,
    // 上传失败的异常信息
    var e: ApiException? = null
)