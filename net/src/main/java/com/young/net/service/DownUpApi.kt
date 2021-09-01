package com.young.net.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 16:59 周六
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

 * @description: 上传下载那些 api
 *
 * 文件上传，参考
 * 再谈Retrofit：文件的上传下载及进度显示
 * https://www.jianshu.com/p/982a005de665/
 *
 * Retrofit 多文件上传进度监听.
 * https://blog.csdn.net/qq_17524035/article/details/80679271
 *
 * Retrofit2 使用@Multipart上传文件和文字
 * https://blog.csdn.net/qq_25330791/article/details/109076710
 */
interface DownUpApi {
    /**
     * 下载
     *
     * [url] 下载的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @Streaming
    @GET
    fun download(
        @Url url: String,
        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<ResponseBody>

    /**
     * 上传
     *
     * [url] 上传接口
     *
     * [body] 请求体
     *
     * [headerMap] 请求头
     */
    @POST
    fun upload(
        @Url url: String,
        @Body body: RequestBody,
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

//    @Multipart
//    @POST("url")
//    fun uploadFile(@Part file: RequestBody?): Call<Result?>?
}