package com.young.net.client.impl

import com.young.net.callback.ICallback
import com.young.net.client.RestBodyClient
import com.young.net.client.base.BaseClient
import okhttp3.RequestBody
import retrofit2.Call
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 23:05 周四
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

 * @description 带 Body 的请求客户端（就是 post 等）
 */
class RestBodyClientImpl<T>(
    url: String,
    resultType: Type,
    headerMap: Map<String, String>,
    // 跟 body 互斥
    paramMap: MutableMap<String, Any>?,
    // 跟 paramMap 互斥（优先级会高于 paramMap）
    val body: RequestBody?
) : BaseClient<T>(url, resultType, headerMap, paramMap), RestBodyClient<T> {

//    constructor(
//        url: String,
//        resultType: Class<T>,
//        headerMap: Map<String, String>,
//        paramMap: MutableMap<String, Any>
//    ) : super(url, resultType, headerMap, paramMap) {
//        mRestClient = RestClientImpl(url, resultType, headerMap, paramMap)
//    }
//
//    constructor(
//        url: String,
//        resultType: TypeToken<T>,
//        headerMap: Map<String, String>,
//        paramMap: MutableMap<String, Any>
//    ) : super(url, resultType, headerMap, paramMap) {
//        mRestClient = RestClientImpl(url, resultType, headerMap, paramMap)
//    }

    private fun getPostCall(): Call<String> {
        val call: Call<String>
        if (body != null) {
            call = getApi().post(url, body, headerMap)
        } else {
            call = getApi().post(url, paramMap ?: mutableMapOf(), headerMap)
        }
        return call
    }

    private fun getPutCall(): Call<String> {
        val call: Call<String>
        if (body != null) {
            call = getApi().put(url, body, headerMap)
        } else {
            call = getApi().put(url, paramMap ?: mutableMapOf(), headerMap)
        }
        return call
    }

    private fun getPatchCall(): Call<String> {
        val call: Call<String>
        if (body != null) {
            call = getApi().patch(url, body, headerMap)
        } else {
            call = getApi().patch(url, paramMap ?: mutableMapOf(), headerMap)
        }
        return call
    }


    override fun post(callback: ICallback<T>) {
        execute(getPostCall(), callback)
    }

    override fun post(): T? {
        return execute(getPostCall())
    }

    override fun put(callback: ICallback<T>) {
        execute(getPutCall(), callback)
    }

    override fun put(): T? {
        return execute(getPutCall())
    }

    override fun patch(callback: ICallback<T>) {
        execute(getPatchCall(), callback)
    }

    override fun patch(): T? {
        return execute(getPatchCall())
    }
}