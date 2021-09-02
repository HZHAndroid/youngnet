package com.young.net.client_creator

import com.google.gson.reflect.TypeToken
import com.young.net.callback.IGetCall
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 10:16 周四
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

 * @description: 基础的客户端构建器
 */
@Suppress("UNCHECKED_CAST")
open class BaseClientCreator<T, C : BaseClientCreator<T, C>> {

    // 请求的 api
    protected val mUrl: String

    // 请求的解析类型
//    protected val mResultType: Class<T>
    protected val mResultType: Type

    // 请求头集合
    protected val mHeaderMap: MutableMap<String, String> by lazy {
        mutableMapOf()
    }

    // 参数集合
    protected val mParamMap: MutableMap<String, Any> by lazy {
        mutableMapOf()
    }

    // 获取 Call<?> 对象回调
    protected var mGetCall: IGetCall? = null

    constructor(url: String, resultType: TypeToken<T>) {
        mUrl = url
        mResultType = resultType.type
    }

    constructor(url: String, resultType: Class<T>) {
        mUrl = url
        mResultType = resultType
    }

    private fun self(): C {
        return this as C
    }

    /**
     * 设置获取 Call 对象回调，方便外部进行关闭等操作
     */
    fun setGetCall(call: IGetCall): C {
        mGetCall = call
        return self()
    }

    /**
     * 添加请求头
     *
     * [key] 请求头 key
     *
     * [value] 请求头 value
     */
    fun addHeader(key: String, value: String): C {
        mHeaderMap[key] = value
        return self()
    }

    /**
     * 添加请求头集合
     *
     * [headers] 请求头集合
     */
    fun addHeaders(headers: Map<String, String>): C {
        if (headers.isNotEmpty()) {
            mHeaderMap.putAll(headers)
        }
        return self()
    }

    /**
     * 添加参数
     *
     * [key] 参数 key
     *
     * [value] 参数值
     */
    fun addParam(key: String, value: Any): C {
        mParamMap[key] = value
        return self()
    }

    /**
     * 添加参数
     *
     * [params] 请求参数集合
     */
    fun addParams(params: Map<String, Any>): C {
        if (params.isNotEmpty()) {
            mParamMap.putAll(params)
        }
        return self()
    }
}