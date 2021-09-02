package com.young.youngnet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.young.net.YoungNetWorking
import com.young.net.callback.ICallback
import com.young.net.callback.IGetCall
import com.young.net.exception.ApiException
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        YoungNetWorking.createBodyClientCreator("user/list", Any::class.java)
            .addParam("hello", "你好")
            .addHeader("token", "tokjskjdf")
            .addHeader("shenxian", "i am power")
            .setGetCall(object :IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong","call call call ${call}")
                }

            })
            .build()
            .post(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    Log.e("shenlong", "kk onFailure = $e")
                }

                override fun onSuccess(data: Any?) {
                    Log.e("shenlong", "kk tset = " + data + " " + Thread.currentThread().name)
                }
            })
    }
}