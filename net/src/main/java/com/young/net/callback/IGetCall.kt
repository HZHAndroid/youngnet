package com.young.net.callback

import retrofit2.Call

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/9/2 10:21 周四
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

 * @description: 获取 retrofit 的 Call<?> 对象，方便外部进行取消等操作
 */
interface IGetCall {
    /**
     * 获取 Call<?> 对象
     */
    fun onGet(call: Call<*>)
}