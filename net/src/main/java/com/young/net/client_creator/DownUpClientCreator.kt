package com.young.net.client_creator

import com.google.gson.reflect.TypeToken
import com.young.net.client.DownUpClient
import com.young.net.client.impl.DownUpClientImpl

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/28 20:04 周六
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

 * @description: 上传/下载 客户端构建者
 */
class DownUpClientCreator<T> : BaseClientCreator<T, DownUpClientCreator<T>>,
    IBuilder<DownUpClient<T>> {

    constructor(url: String, resultType: TypeToken<T>) : super(url, resultType)
    constructor(url: String, resultType: Class<T>) : super(url, resultType)

    override fun build(): DownUpClient<T> {
        return DownUpClientImpl(mUrl, mResultType, mHeaderMap, mParamMap)
    }
}