package com.young.net.client.impl

import com.young.net.callback.ICallback
import com.young.net.callback.IGetCall
import com.young.net.callback.RetrofitCallback
import com.young.net.callback.RetrofitSyncCallback
import com.young.net.client.RestClient
import com.young.net.client.base.BaseClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

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
@Suppress("UNCHECKED_CAST")
class RestClientImpl<T>(
    url: String,
    resultType: Type,
    headerMap: Map<String, String>,
    paramMap: MutableMap<String, Any>,
    callCallback: IGetCall?
) : BaseClient<T>(url, resultType, headerMap, paramMap, callCallback), RestClient<T> {


    private fun getGetCall(): Call<String> {
        return getApi().get(url, paramMap ?: mutableMapOf(), headerMap)
    }

    private fun getDeleteCall(): Call<String> {
        return getApi().delete(url, paramMap ?: mutableMapOf(), headerMap)
    }

    private fun getOptionsCall(): Call<String> {
        return getApi().options(url, paramMap ?: mutableMapOf(), headerMap)
    }

    private fun getHeadCall(): Call<Void> {
        return getApi().head(url, paramMap ?: mutableMapOf(), headerMap)
    }

    override fun get(callback: ICallback<T>) {
        getGetCall().enqueue(RetrofitCallback(callback, resultType,callCallback))
    }

    override fun get(): T? {
        return RetrofitSyncCallback<T>(resultType, getGetCall(),callCallback).execute()
    }

    override fun delete(callback: ICallback<T>) {
        execute(getDeleteCall(), callback)
    }

    override fun delete(): T? {
        return execute(getDeleteCall())
    }


    override fun options(callback: ICallback<T>) {
        execute(getOptionsCall(), callback)
    }

    override fun options(): T? {
        return execute(getOptionsCall())
    }

    override fun head(callback: Callback<Void>) {
        getHeadCall().enqueue(callback)
    }

    override fun head(): Response<Void>? {
        return getHeadCall().execute()
    }
}