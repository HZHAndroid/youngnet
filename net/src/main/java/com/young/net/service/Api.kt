package com.young.net.service

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/23 21:47 周一
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

 * @description: 定义 Retrofit 的接口
 *
 * https://www.jianshu.com/p/bca05b853c8c?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
 *
 * HTTP Request 常用方法GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE
 * https://blog.csdn.net/zane3/article/details/73732986
 *
 * 浅谈http协议六种请求方法，get、head、put、delete、post、options区别
 * https://www.cnblogs.com/wei-hj/p/7859707.html
 *
 * http请求方法（GET、POST、HEAD、OPTIONS、PUT、DELETE、TRACE、CONNECT）
 * https://blog.csdn.net/potato512/article/details/76696582
 */
interface Api {

    /**
     * Get 请求
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @GET
    fun get(
        @Url url: String,
        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>


    /**
     * Post 请求
     *
     * 通过表单的形式提交（使用json提交数据，Content-Type: application/json; charset=utf-8）
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @FormUrlEncoded
    @POST
    fun post(
        @Url url: String,
        @FieldMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    /**
     * Post 请求（通过原数据的形式提交）
     *
     * 通过表单的形式提交
     *
     * [url] 请求的接口
     *
     * [body] 请求体
     * 创建例子：
     * 1、Map<String,Object> paramMap = new HashMap<>();
     * RequestBody requestBody = RequestBody.create(DataParserUtil.toJson(paramMap),  MediaType.parse("application/json; charset=utf-8"));
     *
     * 2、
     * // 文件集合（key：文件名称 value：文件路径）
     * Map<String,String> fileMap = new HashMap<>();
     * MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
     * Set<String> keySet = fileMap.keySet();
     * for (String fileName : keySet) {
     * // builder.addFormDataPart(UP_FILE_KEY, fileName, RequestBody.create(Objects.requireNonNull(UP_FILE_MAP.get(fileName)),
     * //       MediaType.parse("image/\*")));
     * builder.addFormDataPart("file", fileName, RequestBody.create(new File(Objects.requireNonNull(fileMap.get(fileName))),
     * MediaType.parse("星号/星号")));
     * }
     * [headerMap] 请求头
     */
    @POST
    fun post(
        @Url url: String,
        @Body body: RequestBody,
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

//    // 这里是另外一种写法
//    @POST("{path}")
//    fun post(
//        @Path(value = "path" ,encoded = true) url: String,
//        @Body body: RequestBody,
//        @HeaderMap headerMap: Map<String, String> = emptyMap()
//    ): Call<String>


    /**
     * Put 请求
     *
     * 通过表单的形式提交（使用json提交数据，Content-Type: application/json; charset=utf-8）
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @FormUrlEncoded
    @PUT
    fun put(
        @Url url: String,
        @FieldMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    // 跟 get 一样的方式
//    @PUT
//    fun put(
//        @Url url: String,
//        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
//        @HeaderMap headerMap: Map<String, String> = emptyMap()
//    ): Call<String>

    /**
     * Put 请求（通过原数据的形式提交）
     *
     * 通过表单的形式提交
     *
     * [url] 请求的接口
     *
     * [body] 请求体
     *
     * [headerMap] 请求头
     */
    @PUT
    fun put(
        @Url url: String,
        @Body body: RequestBody,
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    /**
     * delete 请求（不支持 body , 跟 Get 一样）
     *
     * 通过表单的形式提交
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @DELETE
    fun delete(
        @Url url: String,
        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    /**
     * Patch 请求
     *
     * 通过表单的形式提交（使用json提交数据，Content-Type: application/json; charset=utf-8）
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @FormUrlEncoded
    @PATCH
    fun patch(
        @Url url: String,
        @FieldMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    /**
     * Patch 请求（通过原数据的形式提交）
     *
     * 通过表单的形式提交
     *
     * [url] 请求的接口
     *
     * [body] 请求体
     *
     * [headerMap] 请求头
     */
    @PATCH
    fun patch(
        @Url url: String,
        @Body body: RequestBody,
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>

    /**
     * Options 请求
     *
     * 通过表单的形式提交（使用json提交数据，Content-Type: application/json; charset=utf-8）
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @OPTIONS
    fun options(
        @Url url: String,
        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<String>


    /**
     * Head 请求(跟 Get 请求一样，不过没有返回 body，服务器在响应HEAD请求时不会回传资源的内容部分，
     * 即：响应主体。这样，我们可以不传输全部内容的情况下，就可以获取服务器的响应头信息。HEAD方法常被用于客户端查看服务器的性能)
     *
     *
     *
     * [url] 请求的接口
     *
     * [paramMap] 请求的参数
     *
     * [headerMap] 请求头
     */
    @HEAD
    fun head(
        @Url url: String,
        @QueryMap paramMap: MutableMap<String, Any> = mutableMapOf(),
        @HeaderMap headerMap: Map<String, String> = emptyMap()
    ): Call<Void>


}