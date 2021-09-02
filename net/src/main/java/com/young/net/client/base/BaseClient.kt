package com.young.net.client.base

import com.young.net.callback.ICallback
import com.young.net.callback.IGetCall
import com.young.net.callback.RetrofitCallback
import com.young.net.callback.RetrofitSyncCallback
import com.young.net.service.Api
import com.young.net.service.DownUpApi
import com.young.net.utils.ApiCreateUtil
import retrofit2.Call
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 11:00 周四
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

 * @description: 基础的客户端
 */
@Suppress("UNCHECKED_CAST")
open class BaseClient<T>(
    // 请求的 api
    protected val url: String,
    // 请求的解析类型
    protected val resultType: Type,
    // 请求头
    protected val headerMap: Map<String, String>,
    // 请求参数
    protected val paramMap: MutableMap<String, Any>?,
    // 获取 retrofit 的 Call<?> 的回调
    protected val callCallback: IGetCall?
) {

//    // 请求的解析类型
//    protected val resultType: Class<T>

    //    constructor(
//        url: String,
//        resultType: Class<T>,
//        headerMap: Map<String, String>,
//        paramMap: MutableMap<String, Any>
//    ) {
//        this.url = url
//        this.resultType = resultType
//        this.headerMap = headerMap
//        this.paramMap = paramMap
//    }
//
//    constructor(
//        url: String,
//        resultType: TypeToken<T>,
//        headerMap: Map<String, String>,
//        paramMap: MutableMap<String, Any>
//    ) {
//        this.url = url
//        this.resultType = resultType.type
//        this.headerMap = headerMap
//        this.paramMap = paramMap
//    }


    protected fun getApi(): Api = ApiCreateUtil.create(Api::class.java)

    protected fun getDownUpApi(): DownUpApi = ApiCreateUtil.createDownService(DownUpApi::class.java)

    protected fun execute(call: Call<String>, callback: ICallback<T>) {
        call.enqueue(RetrofitCallback(callback, resultType,callCallback))
    }

    protected fun execute(call: Call<String>): T? {
        return RetrofitSyncCallback<T>(resultType, call,callCallback).execute()
    }
}