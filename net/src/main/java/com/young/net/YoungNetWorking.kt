package com.young.net

import com.google.gson.reflect.TypeToken
import com.young.net.client_creator.BodyClientCreator
import com.young.net.client_creator.CommonClientCreator
import com.young.net.client_creator.DownUpClientCreator

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 8:46 周四
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

 * @description: 网络请求的工作类
 */
object YoungNetWorking {

    /**
     * 构建公共的请求客户端
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createCommonClientCreator(
        url: String,
        resultType: Class<T>
    ): CommonClientCreator<T> {
        return CommonClientCreator(url, resultType)
    }

    /**
     * 构建公共的请求客户端
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createCommonClientCreator(
        url: String,
        resultType: TypeToken<T>
    ): CommonClientCreator<T> {
        return CommonClientCreator(url, resultType)
    }

    /**
     * 构建带Body等功能的请求客户端
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createBodyClientCreator(
        url: String,
        resultType: Class<T>
    ): BodyClientCreator<T> {
        return BodyClientCreator(url, resultType)
    }

    /**
     * 构建带Body等功能的请求客户端
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createBodyClientCreator(
        url: String,
        resultType: TypeToken<T>
    ): BodyClientCreator<T> {
        return BodyClientCreator(url, resultType)
    }

    /**
     * 构建下载/上传客户端构建者
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createDownUpClientCreator(
        url: String,
        resultType: Class<T>
    ): DownUpClientCreator<T> {
        return DownUpClientCreator(url, resultType)
    }

    /**
     * 构建下载/上传客户端构建者
     *
     * [url] 请求的 url
     *
     * [resultType] 解析的结果类型
     */
    fun <T> createDownUpClientCreator(
        url: String,
        resultType: TypeToken<T>
    ): DownUpClientCreator<T> {
        return DownUpClientCreator(url, resultType)
    }

}