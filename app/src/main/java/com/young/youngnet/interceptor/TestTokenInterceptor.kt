package com.young.youngnet.interceptor

import android.util.Log
import com.young.net.YoungNetWorking.createCommonClientCreator
import com.young.net.interceptor.net.TokenInterceptor

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/29 17:43 周日
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
class TestTokenInterceptor : TokenInterceptor() {
    @Volatile
    private  var mIsExpire = true

    override fun getHeaderMap(): Map<String, String> {
        val map = mutableMapOf<String,String>()
        map["user-ann"] = "android"
        Log.e("shenlong","getHeaderMap() = ${map}")
        return map
    }

    override fun getTokenKeyValue(): Pair<String, String>? {
        Log.e("shenlong","getTokenKeyValue() ")
       return Pair("token","AADkdkdkdkkddddddd")
    }

    override fun isNeedToken(url: String, method: String): Boolean {
        if (url.startsWith("http://rap2api.taobao.org/app/mock/289099/user") && "GET" == method){
            return false
        }
        return true
    }

    override fun onCheckTokenExpire(bodyStr: String): Boolean {

        Log.e("shenlong","onCheckTokenExpire() ${mIsExpire} ")
        return mIsExpire
    }

    override fun onRefreshToken(): Pair<String, String>? {

        var newToken = "onRefreshToken"
        try {
            val obj = createCommonClientCreator("user", Any::class.java)
                .addParam("hello", "送刷卡")
                .addHeader("token", "guankd")
                .build()
                .get()
            newToken = "tianlongkjjdkjfkdjf"
            mIsExpire = false
        } catch (e: Exception) {
            Log.e("shenlong","onRefreshTokendddd = $e")
        }
        Log.e("shenlong","onRefreshToken() ")
        return Pair("token","${newToken}")
    }
}