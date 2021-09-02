package com.young.youngnet

import android.app.Application
import android.widget.Toast

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/9/1 23:11 周三
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

 * @description: 应用程序入口
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initNet()
    }

    /**
     * 初始化网络
     */
    private fun initNet() {
//        NetInit.setBaseUrl("http://rap2api.taobao.org/app/mock/289099/") // 设置基础的请求域名，需要一 / 结尾
//            .setCommonErrorCallback(object : ICommonErrorCallback {
//                override fun onCall(e: ApiException) {
//                    // 只要请求异常，都会调用当前方法
//                    // 假如需要特殊处理，假如国际化，或者想变换一下提示消息，可以进行下面的判断，然后利用自定义的消息
//                    // 进行提示，假如不需要，可以直接使用 e.msg
////                    when (e.code) {
////                        ErrorCode.NETWORK_ERROR -> {
////                            // 网络请求异常
////                        }
////                        ErrorCode.PARSE_ERROR -> {
////                            // 数据解析异常
////                        }
////                        ErrorCode.DOWNLOAD_EMPTY -> {
////                            // 下载的内容为空
////                        }
////                        ErrorCode.UNKNOWN_ERROR -> {
////                            // 其他异常
////                        }
////                    }
//                    showToast(e.msg ?: "")
//                }
//            })
//            .init(object : IDoNetConfig {
//
//                override fun onConfig(config: NetConfig) {
//                    // 普通 api 请求的配置
//                    config
//                        .addInterceptor(TestTokenInterceptor())
//                        .addNetworkInterceptor(
//                            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//                                override fun log(message: String) {
//                                    Log.e("shenlong", "message = $message")
//                                }
//                            }).setLevel(HttpLoggingInterceptor.Level.BODY)
//                        )
//                }
//
//            }, object : IDoNetConfig {
//                override fun onConfig(config: NetConfig) {
//                    // 上传下载 api 请求的配置
//                    // RequestBody writeTo 执行两次问题（722） https://www.jianshu.com/p/705b1c461040
//                    // 从BODY改成HEADERS解决了重复调用问题
//
//                    // okhttp添加日志拦截器，上传文件RequestBody.writeTo调用两次
//                    // https://blog.csdn.net/u013626215/article/details/107014153
//                    config.addNetworkInterceptor(
//                        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//                            override fun log(message: String) {
//                                Log.e("shenlong", "message = $message")
//                            }
//                        }).setLevel(HttpLoggingInterceptor.Level.HEADERS)
//                    )
//                }
//            })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}