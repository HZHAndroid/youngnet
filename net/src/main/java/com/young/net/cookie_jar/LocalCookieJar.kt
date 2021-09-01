package com.young.net.cookie_jar

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 20:53 周三
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

 * @description: Okhttp Cookie 的处理
 */
class LocalCookieJar : CookieJar {

    //cookie的本地化存储
    private val mCache = mutableListOf<Cookie>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (mCache.isEmpty()) {
            return emptyList()
        }
        //过期的cookies
        val invalidCookies: MutableList<Cookie> = ArrayList()
        //有效的cookies
        val validCookies: MutableList<Cookie> = ArrayList()

        for (cookie in mCache) {
            if (cookie.expiresAt < System.currentTimeMillis()) {
                //判断是否过期
                invalidCookies.add(cookie)
            } else if (cookie.matches(url)) {
                //匹配cookie对应的url
                validCookies.add(cookie)
            }

        }
        //缓存中移除过期的cookie
        mCache.removeAll(invalidCookies)
        //返回list<cookie>让request进行设置
        return validCookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        mCache.addAll(cookies)
    }


}