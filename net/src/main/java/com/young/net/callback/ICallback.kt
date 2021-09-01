package com.young.net.callback

import com.young.net.constant.ErrorCode
import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/23 21:50 周一
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

 * @description: 请求结果回调
 */
interface ICallback<T> {

    /**
     * 请求成功
     *
     * [data] 成功后的数据
     */
    fun onSuccess(data: T?)

    /**
     * 请求失败（这里的 code，可以调用 [ErrorCode] 里面的 code 进行判断，然后自定义显示文本）
     *
     * [e] 异常信息
     *
     */
    fun onFailure(e: ApiException)
}