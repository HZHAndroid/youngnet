package com.young.net.client_creator

import com.google.gson.reflect.TypeToken
import com.young.net.client.RestBodyClient
import com.young.net.client.impl.RestBodyClientImpl
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 22:42 周四
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

 * @description:
 */
class BodyClientCreator<T> : BaseClientCreator<T, BodyClientCreator<T>>,
    IBuilder<RestBodyClient<T>> {

    private var body: RequestBody? = null

    constructor(url: String, resultType: TypeToken<T>) : super(url, resultType)
    constructor(url: String, resultType: Class<T>) : super(url, resultType)

    /**
     * 设置 body（优先级会高于 mParamMap）
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
     */
    fun setBody(body: RequestBody): BodyClientCreator<T> {
        this.body = body
        return this
    }

    /**
     * 设置 body
     *
     * [mediaType] 请求数据类型(就是请求头 Content-Type 的值)例如："application/json; charset=utf-8"
     *
     * [content] 字符串类型的数据
     */
    fun setBody(mediaType: MediaType, content: String): BodyClientCreator<T> {
//        RequestBody.create(mediaType, content)
        // 这个转换，需要依赖 com.squareup.okhttp3:logging-interceptor
        this.body = content.toRequestBody(mediaType)
        return this
    }

    override fun build(): RestBodyClient<T> {
        return RestBodyClientImpl(mUrl, mResultType, mHeaderMap, mParamMap, body,mGetCall)
    }
}