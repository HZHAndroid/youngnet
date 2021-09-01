package com.young.net.interceptor.net

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset


/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/29 15:08 周日
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

 * @description: 网络拦截器 - token 刷新处理
 *
 * 参考：
 * Android中使用Retrofit刷新Token
 * https://blog.csdn.net/GD_W001/article/details/94173120
 */
abstract class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 有些请求需要附带请求头（或者统一设置 token 这些）
        var originalRequest = addHeaders(chain.request())
        // 获取响应对象
        val response = chain.proceed(originalRequest)

        try {
            if (isNeedToken(originalRequest.url.toString(), originalRequest.method)) {
                val bodyStr = getBodyStr(response)
                if (!TextUtils.isEmpty(bodyStr)) {
                    val tokenPair = getNewestToken(bodyStr!!)
                    if (tokenPair != null) {
                        // 需要重新创建请求来替换 token
                        originalRequest = originalRequest.newBuilder()
                            .header(tokenPair.first, tokenPair.second)
                            .build()
                        // 重新发起请求
                        return chain.proceed(originalRequest)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    /**
     * 添加请求头
     *
     * [originalRequest] 原来的请求对象
     */
    private fun addHeaders(originalRequest: Request): Request {
        val headerMap = getHeaderMap()
        val tokenKeyValue = getTokenKeyValue()
        if (headerMap.isNotEmpty() || tokenKeyValue != null) {
            val builder = originalRequest.newBuilder()

            if (headerMap.isNotEmpty()) {
                for (entry in headerMap) {
                    // 下面添加请求头，一定不可以使用 addHeader ， 需要使用 header
                    // addHeader : 是不断添加，到时候请求体会不断叠加，如果要避免，需要调用 removeHeader
                    // header : 如果 key 相同，则进行替换，不会出现 addHeader 的情况，
                    builder.header(entry.key, entry.value)
                }
            }

            // 设置 token
            tokenKeyValue?.let {
                builder.header(it.first, it.second)
            }
            return builder.build()
        }
        return originalRequest
    }


    /**
     * 获取请求内容
     *
     * [response] 响应对象
     *
     * [@return] 请求结果
     */
    private fun getBodyStr(response: Response): String? {
        val responseBody: ResponseBody? = response.body
        //解决response.body().string();只能打印一次
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        // Buffer the entire body.
//        val buffer: Buffer = source.buffer()
        val buffer = source?.buffer
        return buffer?.clone()?.readString(Charset.forName("UTF-8"))
    }


    /**
     * 获取最新的 token
     *
     * [bodyStr] 请求的数据，可以解析出来，根据具体业务判断 token 是否过期
     *
     * [@return] 返回 null 则刷新 token，反之不需要刷新 token
     */
    @Synchronized
    private fun getNewestToken(bodyStr: String): Pair<String, String>? {
        if (onCheckTokenExpire(bodyStr)) {
            // token 过期，需要刷新 token
            return onRefreshToken()
        }
        return null
    }

    /**
     * 获取请求头集合（例如：公共请求头等）
     *
     * 注：刚触发拦截器的 intercept 方法的时候，会调用
     */
    protected abstract fun getHeaderMap(): Map<String, String>

    /**
     * 获取 token 的健值对，然后放到请求头
     *
     * 注：刚触发拦截器的 intercept 方法的时候，会调用
     *
     * [@return]
     * first: 请求头的key
     * second：token
     */
    protected abstract fun getTokenKeyValue(): Pair<String, String>?

    /**
     * 校验当前的请求是否需要 token (用于过滤某些不需要 token 的链接，例如 登录接口等)
     *
     * [url] 请求的 url，例如：http://rap2api.taobao.org/app/mock/289099/user/list，
     * 有些 get 请求，会拼接参数，导致跟我们原来的接口链接不一致，例如：http://rap2api.taobao.org/app/mock/289099/user?hello=%E9%80%81%E5%88%B7%E5%8D%A1
     * 所以到时候判断，可以调用 url参数的 startWith 方法，例如：url.starWith(原本的接口域名+接口) 进行匹配，
     *
     * [method] 请求方法，例如：POST，因为 restful api 可能请求接口是同一个
     */
    protected abstract fun isNeedToken(url: String, method: String): Boolean

    /**
     * 校验 token 是否过期
     *
     * [bodyStr] 请求的数据，可以解析出来，根据具体业务判断 token 是否过期
     *
     * [@return]
     * true：token 过期
     * false：token 没有过期
     */
    protected abstract fun onCheckTokenExpire(bodyStr: String): Boolean

    /**
     * 刷新 token 操作（例如请求接口刷新 token）
     *
     * [@return] 新的 token 的 key 和 value
     * first: 请求头的key
     * second：token
     */
    protected abstract fun onRefreshToken(): Pair<String, String>?
}