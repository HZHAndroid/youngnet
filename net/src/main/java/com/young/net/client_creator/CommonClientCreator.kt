package com.young.net.client_creator

import com.google.gson.reflect.TypeToken
import com.young.net.client.RestClient
import com.young.net.client.impl.RestClientImpl

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 10:26 周四
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

 * @description: 公共参数的客户端构建者（例如 Get 请求这些）
 */
class CommonClientCreator<T> :
    BaseClientCreator<T, CommonClientCreator<T>>, IBuilder<RestClient<T>> {

    constructor(url: String, resultType: TypeToken<T>) : super(url, resultType)
    constructor(url: String, resultType: Class<T>) : super(url, resultType)

    override fun build(): RestClient<T> {
        return RestClientImpl(mUrl, mResultType, mHeaderMap, mParamMap,mGetCall)
    }

}