package com.young.net.callback

import com.young.net.constant.ErrorCode
import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 13:38 周四
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

 * @description: 网络请求全局异常监听（用于部分信息全局提示）
 */
interface ICommonErrorCallback {

    /**
     * 请求失败（这里的 code，可以调用 [ErrorCode] 里面的 code 进行判断，然后自定义显示文本）
     *
     * [e] 异常信息
     *
     */
    fun onCall(e: ApiException)

}