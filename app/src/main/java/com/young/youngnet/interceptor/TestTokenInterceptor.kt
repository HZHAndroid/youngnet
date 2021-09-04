package com.young.youngnet.interceptor

import android.os.SystemClock
import com.young.net.YoungNetWorking
import com.young.net.interceptor.net.TokenInterceptor
import com.young.youngnet.constant.Constant

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
    private var mIsExpire = true

    override fun getHeaderMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["power-man"] = "young"
        return map
    }

    override fun getTokenKeyValue(): Pair<String, String>? {
        return Pair(
            "token",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZG1pbiIsImV4cCI6MTYyNzUzOTUzMSwiaWF0IjoxNjI3NDUzMTMxfQ.XD-yOik8P5WoFSj6yPova9fbylKp9lnEy0i-S-KaWxQ"
        )
    }

    override fun isNeedToken(url: String, method: String): Boolean {
        if (url.startsWith("${Constant.Host.HOST}refresh-token") && "GET" == method) {
            return false
        }
        return true
    }

    override fun onCheckTokenExpire(bodyStr: String): Boolean {
        return mIsExpire
    }

    override fun onRefreshToken(): Pair<String, String>? {
        var newToken: String? = null
        try {
            val map = YoungNetWorking.createCommonClientCreator("refresh-token", Map::class.java)
                .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
                .build()
                .get()

            newToken = map?.get("token")?.toString()
            mIsExpire = false
        } catch (e: Exception) {
        }
        return Pair("token", "${newToken}")
    }
}