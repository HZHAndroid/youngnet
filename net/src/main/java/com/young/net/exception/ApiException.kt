package com.young.net.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.young.net.constant.ErrorCode
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/23 21:51 周一
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

 * @description: 网络请求异常类
 */
class ApiException(
    val code: Int, // 异常 code
    val msg: String? // 异常消息
) : Exception(msg) {

    companion object {
//        // 其它异常
//        private const val UNKNOWN_ERROR = -0x10
//
//        // 数据解析异常
//        private const val PARSE_ERROR = -0x11
//
//        // 网络请求异常
//        private const val NETWORK_ERROR = -0x12


        /**
         * Throwable --> ApiException
         *
         * @param e
         * @return
         */
        fun handleException(e: Throwable): ApiException {
            if (e is ApiException) {
                return e
            }
            val ex: ApiException
            return if (e is JsonParseException
                || e is JSONException
                || e is ParseException
            ) {
                ex = ApiException(ErrorCode.PARSE_ERROR, "数据解析异常")
                ex
            } else if (e is ConnectException
                || e is UnknownHostException
                || e is SocketTimeoutException
            ) {
                ex = ApiException(ErrorCode.NETWORK_ERROR, "网络请求异常")
                ex
            } else {
                ex = ApiException(ErrorCode.UNKNOWN_ERROR, "其它异常：" + e.message)
                e.printStackTrace()
                ex
            }
        }
    }


    override fun toString(): String {
        return "ApiException(code=$code, msg=$msg)"
    }
}