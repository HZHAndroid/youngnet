package com.young.net.init

import com.young.net.creator.MergeOkhttpRetrofitCreator
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 21:18 周三
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

 * @description: 普通的网络请求初始化类
 */
class NetConfig {

    private val mMergeCreator by lazy {
        MergeOkhttpRetrofitCreator()
    }

    // 是否重新创建 Retrofit
    private var mReNewRetrofit = false

    // Retrofit 对象，初始化就是为了这个
    private lateinit var mRetrofit: Retrofit

    private fun getRetrofitBuilder(): Retrofit.Builder = mMergeCreator.retrofitCreator.builder

    private fun getOkHttpClientBuilder(): OkHttpClient.Builder = mMergeCreator.clientCreator.builder

    fun getRetrofit(): Retrofit {
        if (::mRetrofit.isInitialized) {
            return mRetrofit
        }
        throw Exception("请先调用 init 方法进行初始化，例如：NetConfig#init ")
    }

    /**
     * 最后必须调用的初始化(调用这个方法去完成初始化)
     *
     * [baseUrl] retrofit 请求的基础 url，以 / 结尾
     *
     */
    fun init(baseUrl: String) {
        if (::mRetrofit.isInitialized && !mReNewRetrofit) {
            // lateinit 修饰的变量，可以这样判断是否初始化
            return
        }
        mRetrofit = getRetrofitBuilder()
            .baseUrl(baseUrl)
            .client(getOkHttpClientBuilder().build())
            .build()
        mReNewRetrofit = false
    }


    /**
     * 设置是否重新创建 Retrofit （调用完成，记得重新调用 init）
     *
     * [reNewRetrofit] true: 重新创建 false: 不重新创建
     */
    fun reNewRetrofit(reNewRetrofit: Boolean = false): NetConfig {
        mReNewRetrofit = reNewRetrofit
        return this
    }

    /**
     * 替换掉 OkHttpClient.Builder （替换之前设置的参数，会被覆盖）
     */
    fun replace(builder: OkHttpClient.Builder): NetConfig {
        mMergeCreator.clientCreator.builder = builder
        return this
    }

    /**
     * 替换掉 Retrofit.Builder （替换之前设置的参数，会被覆盖）
     */
    fun replace(builder: Retrofit.Builder): NetConfig {
        mMergeCreator.retrofitCreator.builder = builder
        return this
    }

    // okhttp

    /**
     * 完整请求超时时长，从发起到接收返回的数据，默认值为0，不限定
     *
     * [timeOut] 时间，单位：秒
     */
    fun setCallTimeout(timeOut: Long): NetConfig {
        getOkHttpClientBuilder().callTimeout(timeOut, TimeUnit.SECONDS)
        return this
    }

    /**
     * 与服务器建立连接的时长，默认10s
     *
     * [timeOut] 时间，单位：秒
     */
    fun setConnectTimeout(timeOut: Long): NetConfig {
        getOkHttpClientBuilder().connectTimeout(timeOut, TimeUnit.SECONDS)
        return this
    }

    /**
     * 读取服务器返回数据的时长
     *
     * [timeOut] 时间，单位：秒
     */
    fun setReadTimeout(timeOut: Long): NetConfig {
        getOkHttpClientBuilder().readTimeout(timeOut, TimeUnit.SECONDS)
        return this
    }

    /**
     * 向服务器写入数据的时长，默认10s
     *
     * [timeOut] 时间，单位：秒
     */
    fun writeTimeout(timeOut: Long): NetConfig {
        getOkHttpClientBuilder().writeTimeout(timeOut, TimeUnit.SECONDS)
        return this
    }

    /**
     * 设置缓存路径
     *
     * [path] 缓存路径
     *
     * [maxSize] 最大缓存的值，单位：kb
     */
    fun cache(path: String, maxSize: Long): NetConfig {
        getOkHttpClientBuilder().cache(Cache(File(path), maxSize))
        return this
    }

    /**
     * 设置 Cookie 的处理类
     *
     * [cookieJar] cookie 的处理类
     */
    fun cookieJar(cookieJar: CookieJar): NetConfig {
        getOkHttpClientBuilder().cookieJar(cookieJar)
        return this
    }

    /**
     * 设置是否重连
     *
     * [retryOnConnectionFailure] true: 重连 false：不重连
     */
    fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean): NetConfig {
        getOkHttpClientBuilder().retryOnConnectionFailure(retryOnConnectionFailure)
        return this
    }

    /**
     * 设置是否重连
     *
     * [followRedirects] true: 重连 false：不重连
     */
    fun followRedirects(followRedirects: Boolean): NetConfig {
        getOkHttpClientBuilder().followRedirects(followRedirects)
        return this
    }

    /**
     * 添加网络拦截器（跟普通的拦截器相比较，网络拦截器，网络信息会更全点，
     * 例如：网络日志拦截器、公共请求头拦截器、token拦截器等，就需要使用这个）
     *
     * [interceptor] 网络拦截器
     */
    fun addNetworkInterceptor(interceptor: Interceptor): NetConfig {
        getOkHttpClientBuilder().addNetworkInterceptor(interceptor)
        return this
    }

    /**
     * 添加网络拦截器集合（跟普通的拦截器相比较，网络拦截器，网络信息会更全点，
     * 例如：网络日志拦截器、公共请求头拦截器、token拦截器等，就需要使用这个）
     *
     * [interceptors] 网络拦截器集合
     */
    fun addNetworkInterceptors(interceptors: List<Interceptor>): NetConfig {
        for (interceptor in interceptors) {
            getOkHttpClientBuilder().addNetworkInterceptor(interceptor)
        }
        return this
    }

    /**
     * 添加普通拦截器
     *
     * [interceptor] 普通拦截器
     */
    fun addInterceptor(interceptor: Interceptor): NetConfig {
        getOkHttpClientBuilder().addInterceptor(interceptor)
        return this
    }

    /**
     * 添加普通拦截器集合
     *
     * [interceptors] 普通拦截器集合
     */
    fun addInterceptors(interceptors: List<Interceptor>): NetConfig {
        for (interceptor in interceptors) {
            getOkHttpClientBuilder().addInterceptor(interceptor)
        }
        return this
    }

    // retrofit

    /**
     * 添加数据解析工厂类（这里是我们解析的最终目标数据，就是最后拿来用的，也就是 Call<T> 中的 T）
     *
     * [factory] 数据解析工厂
     */
    fun addConverterFactory(factory: Converter.Factory): NetConfig {
        getRetrofitBuilder().addConverterFactory(factory)
        return this
    }

    /**
     * 添加返回类型的支持（例如：Call、LiveData、Observer）
     *
     * [factory] 返回类型工厂
     */
    fun addCallAdapterFactory(factory: CallAdapter.Factory): NetConfig {
        getRetrofitBuilder().addCallAdapterFactory(factory)
        return this
    }

}