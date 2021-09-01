package com.young.net.client

import com.young.net.callback.ICallback
import com.young.net.exception.ApiException
import retrofit2.Callback
import retrofit2.Response

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 0:58 周四
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

 * @description: 请求类
 */
interface RestClient<T> {


    /**
     * Get 请求
     *
     * [callback] 请求回调
     */
    fun get(callback: ICallback<T>)

    /**
     * Get 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun get(): T?

    /**
     * Delete 请求
     *
     * [callback] 请求回调
     */
    fun delete(callback: ICallback<T>)

    /**
     * Delete 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun delete(): T?

    /**
     * Options 请求
     *
     * [callback] 请求回调
     */
    fun options(callback: ICallback<T>)

    /**
     * Options 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun options(): T?

    /**
     * Head 请求
     *
     * [callback] 请求回调
     */
    fun head(callback: Callback<Void>)

    /**
     * Head 请求
     *
     * [@return] 解析的数据
     */
    @Throws(ApiException::class)
    fun head(): Response<Void>?
}