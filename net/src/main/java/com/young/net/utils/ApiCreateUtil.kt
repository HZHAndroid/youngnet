package com.young.net.utils

import android.util.Log
import com.young.net.NetInit
import java.util.*

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 23:16 周三
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

 * @description: Retrofit 的 Api 创建工具类
 */
object ApiCreateUtil {
    // 缓存 service
    private val mServiceMap: WeakHashMap<Any, Any> = WeakHashMap()

    /**
     * 构建一个 service
     *
     * [service] api 的 service 类型
     */
    fun <T> create(service: Class<T>): T {
        var any = mServiceMap[service]
        if (any == null) {
            any = NetInit.getRetrofit().create(service)
            mServiceMap[service] = any
        }
        @Suppress("UNCHECKED_CAST")
        return any as T
    }

    /**
     * 利用 上传/下载的 retrofit 构建一个 service
     *
     * [service] api 的 service 类型
     */
    fun <T> createDownService(service: Class<T>): T {
        var any = mServiceMap[service]
        if (any == null) {
            any = NetInit.getDownUPRetrofit().create(service)
            mServiceMap[service] = any
        }
        @Suppress("UNCHECKED_CAST")
        return any as T
    }
}