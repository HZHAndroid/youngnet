package com.young.net.creator

import com.young.net.creator.OkHttpClientCreator

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 21:06 周三
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

 * @description: 关联 OkHttp 和 Retrofit 构建者
 *
 */
class MergeOkhttpRetrofitCreator(
    // Okhttp 客户端创建者
    val clientCreator: OkHttpClientCreator = OkHttpClientCreator(),
    // Retrofit 创建者
    val retrofitCreator: RetrofitCreator = RetrofitCreator()
) {

//    /**
//     * 获取 OkHttpClient
//     */
//    private fun getOkHttpClient(): OkHttpClient {
//        return clientCreator.builder.build()
//    }
//
//    /**
//     * 构建出 Retrofit
//     */
//    fun build(): Retrofit {
//        return retrofitCreator.builder
//            .client(getOkHttpClient())
//            .build()
//    }
}