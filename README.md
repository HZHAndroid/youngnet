[![Version](https://jitpack.io/v/HZHAndroid/youngnet.svg)](https://jitpack.io/#FunnySaltyFish/CMaterialColors)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/HZHAndroid/youngnet/blob/main/LICENSE)

## 介绍
```
**youngnet** 这个网络库，是基于 retofit 进行封装的。该框架支持 restful api 的请求。
支持的请求方式有：get、post、put、delete、options、patch、head

支持自定义数据解析对象
支持Token过期自动刷新模板拦截器
支持设置 Retrofit 的 addConverterFactory 和 addCallAdapterFactory
支持设置 Okhttp 的 addInterceptor 和 addNetworkInterceptor

当前框架，对上面的方式归为了三个构建者：
- 1、YoungNetWorking#createCommonClientCreator：这个构建者，不包含请求体的，
支持：get、delete、options、head
同步和异步调用都支持

- 2、YoungNetWorking#createBodyClientCreator：这个构建者，包含请求体，
支持：post、put、patch
同步和异步调用都支持

- 2、YoungNetWorking#createDownUpClientCreator：这个构建者，做上传和下载的，
支持：单文件上传、多文件上传、单文件下载、多文件下载，
支持上传/下载的子进度回调、总进度回调
同步和异步调用都支持

```

## Demo 运行效果图
![screen_1.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/1.png)
![screen_2.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/2.png)
![screen_3.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/3.png)
![screen_4.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/4.png)
![screen_5.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/5.png)
![screen_6.png](https://github.com/HZHAndroid/youngnet/blob/main/demo-images/6.png)

## 使用
### 一、初始化
在你的 application 中的 onCreate 方法中调用下面的代码，进行初始化配置：
可以参考[这里](https://github.com/HZHAndroid/youngnet/blob/main/app/src/main/java/com/young/youngnet/MyApplication.kt)

```kotlin

 NetInit.setBaseUrl(Constant.Host.HOST) // 设置基础的请求域名，需要一 / 结尾
            .setCommonErrorCallback(object : ICommonErrorCallback {
                override fun onCall(e: ApiException) {
                    // 只要请求异常，都会调用当前方法
                    // 假如需要特殊处理，假如国际化，或者想变换一下提示消息，可以进行下面的判断，然后利用自定义的消息
                    // 进行提示，假如不需要，可以直接使用 e.msg
//                    when (e.code) {
//                        ErrorCode.NETWORK_ERROR -> {
//                            // 网络请求异常
//                        }
//                        ErrorCode.PARSE_ERROR -> {
//                            // 数据解析异常
//                        }
//                        ErrorCode.DOWNLOAD_EMPTY -> {
//                            // 下载的内容为空
//                        }
//                        ErrorCode.UNKNOWN_ERROR -> {
//                            // 其他异常
//                        }
//                    }
                    showToast(e.msg ?: "")
                }
            })
            .init(object : IDoNetConfig {

                override fun onConfig(config: NetConfig) {
                    // 普通 api 请求的配置
                    config
                        .addInterceptor(TestTokenInterceptor())
                        .addNetworkInterceptor(
                            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                                override fun log(message: String) {
                                    Log.e("shenlong", "message = $message")
                                }
                            }).setLevel(HttpLoggingInterceptor.Level.BODY)
                        )
                }

            }, object : IDoNetConfig {
                override fun onConfig(config: NetConfig) {
                    // 上传下载 api 请求的配置
                    // RequestBody writeTo 执行两次问题（722） https://www.jianshu.com/p/705b1c461040
                    // 从BODY改成HEADERS解决了重复调用问题

                    // okhttp添加日志拦截器，上传文件RequestBody.writeTo调用两次
                    // https://blog.csdn.net/u013626215/article/details/107014153
                    config.addNetworkInterceptor(
                        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                            override fun log(message: String) {
                                Log.e("shenlong", "message = $message")
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    )
                }
            })
```

### 二、代码调用

- 1、YoungNetWorking#createCommonClientCreator，具体的请求参考[这里](https://github.com/HZHAndroid/youngnet/blob/main/app/src/main/java/com/young/youngnet/demo/CommonClientCreatorActivity.kt)：

```kotlin

    YoungNetWorking.createCommonClientCreator("user", Any::class.java)
            .addParam("userId", "${SystemClock.currentThreadTimeMillis()}")
            .addHeader("agent","android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .get(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult(e.msg)
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult(data?.toString())
                    hideLoading()
                }
            })
```

- 2、YoungNetWorking#createBodyClientCreator，具体的请求参考[这里](https://github.com/HZHAndroid/youngnet/blob/main/app/src/main/java/com/young/youngnet/demo/BodyClientCreatorActivity.kt)：
```kotlin
        val paramMap = mutableMapOf<String, Any>()
        paramMap["userId"] = "${SystemClock.currentThreadTimeMillis()}"
        paramMap["bookId"] = "${SystemClock.currentThreadTimeMillis()}"

        YoungNetWorking.createBodyClientCreator("book", Any::class.java)
            .setBody(
                JsonUtil.toJson(paramMap)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            )
            .addHeader("agent", "android-app")
            .setGetCall(object : IGetCall {
                override fun onGet(call: Call<*>) {
                    Log.e("shenlong", "call call call ${call}")
                }

            })
            .build()
            .post(object : ICallback<Any> {
                override fun onFailure(e: ApiException) {
                    showResult("post ${e.msg}")
                    hideLoading()
                }

                override fun onSuccess(data: Any?) {
                    showResult("post ${data?.toString()}")
                    hideLoading()
                }
            })
```

- 3、YoungNetWorking#createDownUpClientCreator，具体的请求参考[这里](https://github.com/HZHAndroid/youngnet/blob/main/app/src/main/java/com/young/youngnet/demo/DownUpClientCreatorActivity.kt)：
```kotlin
    下载：
        val file = File(cacheDir, "hello.jpg")
        YoungNetWorking.createDownUpClientCreator(
            "https://ae01.alicdn.com/kf/Uf8cd7a9ee0054a1c85f95633ccb722fc3.jpg",
            String::class.java
        )
            .addParam("hello", "hi")
            .addHeader(
                "shenlong",
                "tian"
            )
            .build()
            .download(file, getDownloadCallback("download"))

    上传：
        YoungNetWorking.createDownUpClientCreator(
            "https://imgbb.com/json",
            Any::class.java
        )
            .addParam("action", "upload")
            .addParam("auth_token", "232c8e0cdb5e960cfc2233b80bd8ad7345b17090")
            .addParam("timestamp", "1630168059700")
            .addParam("type", "file")
            .build()
            .upload("source", file, getUploadCallback(stringBuffer),
                object : ICallback<Any> {
                    override fun onFailure(e: ApiException) {
                        stringBuffer.append("onFailure = ${e.msg} ${e.message}")
                        showResult(stringBuffer.toString())
                    }

                    override fun onSuccess(data: Any?) {
                        stringBuffer.append("onSuccess = ${data}")
                        showResult(stringBuffer.toString())
                    }

                })
```

### 三、自定义数据解析对象
```kotlin
可以创建一个类继承 IDataParser 去实现解析框架的替换
NetInit.setBaseUrl(Constant.Host.HOST) // 设置基础的请求域名，需要一 / 结尾
            .dataParser(object :IDataParser() {
                override fun <T> parseJson(jsonStr: String, dataType: Type): T {
                    return null;
                }
            })
```

### 四、自定义token过期自动刷新拦截器

创建一个类继承 TokenInterceptor ，然后实现其中的抽象方法即可，
可以参考[这里](https://github.com/HZHAndroid/youngnet/blob/main/app/src/main/java/com/young/youngnet/interceptor/TestTokenInterceptor.kt)


### 四、其他
```
1、当前项目已经加入了混淆配置，不需要额外添加混淆配置；
2、如果自己增加了 Retrofit 的请求方法，可以通过 ApiCreateUtil#create 或者 ApiCreateUtil#createDownService 去创建对应的 api 请求类；
```