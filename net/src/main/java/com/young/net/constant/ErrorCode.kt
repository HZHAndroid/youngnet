package com.young.net.constant

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 13:22 周四
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

 * @description: 自己封装的异常 code （提供给外部进行判断）
 */
object ErrorCode {

    // 其它异常
    const val UNKNOWN_ERROR = -0x10

    // 数据解析异常
    const val PARSE_ERROR = -0x11

    // 网络请求异常
    const val NETWORK_ERROR = -0x12

    // 下载的内容为空
    const val DOWNLOAD_EMPTY = -0x13

}