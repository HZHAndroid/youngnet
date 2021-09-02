package com.young.net.callback

import android.os.Looper
import com.young.net.NetInit
import com.young.net.exception.ApiException
import com.young.net.utils.RunOnUIUtil
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 18:29 周四
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

 * @description: Retrofit 的同步请求回调
 */
@Suppress("UNCHECKED_CAST")
class RetrofitSyncCallback<T>(
//    private val resultType: Class<T>,
    private val resultType: Type,
    private val call: Call<String>,
    // 获取 Call<?> 对象的回调
    private val callCallback: IGetCall?
) {

    @Throws(ApiException::class)
    fun execute(): T? {
        callCallback?.onGet(call)
        try {
            val response = call.execute()

            if (response.isSuccessful) {
                val bodyStr = response.body()
                if (bodyStr == null) {
                    return null
                } else {
                    if (isPass()) {
                        return bodyStr as T
                    } else {
                        return NetInit.getDataParse()
                            .parseJson<T>(bodyStr, resultType)
                    }
                }
            } else {
                var message = response.errorBody()?.toString()
                if (message == null || message.isEmpty()) {
                    message = response.message()
                }
                throw ApiException(response.code(), message)
            }

        } catch (e: Exception) {
            val exception = ApiException.handleException(e)
            RunOnUIUtil.runOnUI {
                NetInit.commonErrorCallback?.onCall(exception)
            }
            throw exception
        }
    }

    /**
     * 是否略过解析
     *
     * [@return] true: 不解析 false: 解析
     */
    private fun isPass(): Boolean {
        return resultType == String::class.java
    }
}