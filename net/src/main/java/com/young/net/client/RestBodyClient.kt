package com.young.net.client

import com.young.net.callback.ICallback
import com.young.net.exception.ApiException

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 23:03 周四
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

 * @description: 带 Body 的请求客户端（就是 post 等）
 */
interface RestBodyClient<T> {
    /**
     * Post 请求
     *
     * [callback] 请求回调
     */
    fun post(callback: ICallback<T>)

    /**
     * Post 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun post(): T?

    /**
     * Put 请求
     *
     * [callback] 请求回调
     */
    fun put(callback: ICallback<T>)

    /**
     * Put 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun put(): T?


    /**
     * Patch 请求
     *
     * [callback] 请求回调
     */
    fun patch(callback: ICallback<T>)

    /**
     * Patch 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun patch(): T?

}