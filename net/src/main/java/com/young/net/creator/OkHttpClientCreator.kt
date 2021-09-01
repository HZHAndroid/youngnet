package com.young.net.creator

import com.young.net.cookie_jar.LocalCookieJar
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 14:27 周三
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

 * @description: OkHttp 请求客户端构建类
 */
@Suppress("PrivatePropertyName")
class OkHttpClientCreator {
    // 完整请求超时时长，从发起到接收返回的数据，默认值为0，不限定(单位：秒)
    private val TIMEOUT_CALL = 10L

    // 与服务器建立连接的时长，默认10s
    private val TIMEOUT_CONNECT = 10L

    // 读取服务器返回数据的时长
    private val TIMEOUT_READ = 10L

    // 向服务器写入数据的时长，默认10s
    private val TIMEOUT_WRITE = 10L

    // 请求失败的时候，进行重连
    private val RETRY_CONNECTION = false

    // 获取默认配置的 OkHttpClient 的构建者
    var builder: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(TIMEOUT_CALL, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回的数据，默认值为0，不限定
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(RETRY_CONNECTION)//重连
//            .cache(Cache(File("sdcard/cache", "okhttp"), 1024))
//        .cache(Cache(File(PathUtils.getInternalAppCachePath()), 1024))
        .cookieJar(LocalCookieJar())
        .followRedirects(false)//重定向

}