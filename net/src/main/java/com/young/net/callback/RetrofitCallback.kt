package com.young.net.callback

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.young.net.callback.ICallback
import com.young.net.NetInit
import com.young.net.exception.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/26 11:24 周四
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

 * @description: Retrofit 的异步请求回调
 */
@Suppress("UNCHECKED_CAST")
class RetrofitCallback<T>(
    private val callback: ICallback<T>,
//    private val resultType: Class<T>
    private val resultType: Type,
    // 获取 Call<?> 对象的回调
    private val callCallback: IGetCall?
) : Callback<String> {
    // 消息类型 - 成功
    private val MESSAGE_TYPE_SUCCESS = 0x10

    // 消息类型 - 失败
    private val MESSAGE_TYPE_FAILURE = 0x11

    private val mHandler by lazy {
        Handler(Looper.getMainLooper(), object : Handler.Callback {

            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    MESSAGE_TYPE_SUCCESS -> {
                        onSuccess(mResult)
                        return true
                    }
                    MESSAGE_TYPE_FAILURE -> {
                        onFailure(mApiException!!)
                        return true
                    }
                }
                return false
            }

        })
    }

    private var mApiException: ApiException? = null

    private var mResult: T? = null

    override fun onResponse(call: Call<String>, response: Response<String>) {
        callCallback?.onGet(call)
        mApiException = null
        mResult = null
        if (response.isSuccessful) {
            val bodyStr = response.body()
            if (bodyStr == null) {
                mResult = null
                mHandler.obtainMessage(MESSAGE_TYPE_SUCCESS).sendToTarget()
            } else {
                if (isPass()) {
                    mResult = bodyStr as T
                    mHandler.obtainMessage(MESSAGE_TYPE_SUCCESS).sendToTarget()
                } else {
                    try {
                        mResult = NetInit.getDataParse()
                            .parseJson<T>(bodyStr, resultType)
                        mHandler.obtainMessage(MESSAGE_TYPE_SUCCESS).sendToTarget()
                    } catch (e: Exception) {
                        mApiException = ApiException.handleException(e)
                        mHandler.obtainMessage(MESSAGE_TYPE_FAILURE).sendToTarget()
                    }
                }
            }
        } else {
            var message = response.errorBody()?.toString()
            if (message == null || message.isEmpty()) {
                message = response.message()
            }
            mApiException = ApiException(response.code(), message)
            mHandler.obtainMessage(MESSAGE_TYPE_FAILURE).sendToTarget()
        }
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        mApiException = ApiException.handleException(t)
        mHandler.obtainMessage(MESSAGE_TYPE_FAILURE).sendToTarget()
    }

    private fun onSuccess(data: T?) {
        callback.onSuccess(data)
    }

    private fun onFailure(e: ApiException) {
        callback.onFailure(e)
        NetInit.commonErrorCallback?.onCall(e)
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