package com.young.net

import com.young.net.callback.ICommonErrorCallback
import com.young.net.callback.IDoNetConfig
import com.young.net.init.NetConfig
import com.young.net.parse.DataParser
import com.young.net.parse.IDataParser
import retrofit2.Retrofit

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/25 21:18 周三
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

 * @description: 网络初始化类
 */
object NetInit {
    // 普通网络请求的配置
    private val mNetConfig by lazy {
        NetConfig()
    }

    // 下载/上传请求的配置
    private val mDownUploadNetConfig by lazy {
        NetConfig()
    }

    // 数据解析器
    private var mDataParser: IDataParser = DataParser()

    // 全局网络请求异常监听回调
    var commonErrorCallback: ICommonErrorCallback? = null

    // 普通的网络请求基础 url
    private var mBaseUrl: String? = null

    // 下载/删除的网络请求基础 url
    private var mBaseDownUrl: String? = null

    fun getRetrofit(): Retrofit {
        return mNetConfig.getRetrofit()
    }

    fun getDownUPRetrofit(): Retrofit {
        return mDownUploadNetConfig.getRetrofit()
    }

    /**
     * 获取数据解析器
     */
    fun getDataParse(): IDataParser {
        return mDataParser
    }

    /**
     * 设置普通请求的基础 url ，使用 / 结尾
     *
     * [baseUrl] 普通网络的请求基础 url
     */
    fun setBaseUrl(baseUrl: String): NetInit {
        mBaseUrl = baseUrl
        return this
    }

    /**
     * 设置下载/上传请求的基础 url ，使用 / 结尾
     *
     * [baseDownUrl] 下载/上传请求的基础 url
     */
    fun setBaseDownUrl(baseDownUrl: String): NetInit {
        mBaseDownUrl = baseDownUrl
        return this
    }

    /**
     * 初始化
     *
     * [normalNetConfig] 普通的网络请求配置回调
     *
     * [downUploadNetConfig] 下载/上传网络回调
     */
    fun init(normalNetConfig: IDoNetConfig, downUploadNetConfig: IDoNetConfig) {
        normalNetConfig.onConfig(mNetConfig)
        downUploadNetConfig.onConfig(mDownUploadNetConfig)

        if (mBaseUrl == null) {
            mBaseUrl = "https://www.baidu.com/"
        }
        if (mBaseDownUrl == null) {
            mBaseDownUrl = mBaseUrl
        }
        mNetConfig.init(mBaseUrl!!)
        mDownUploadNetConfig.init(mBaseDownUrl!!)
    }

    /**
     * 设置全局异常监听回调
     *
     * [callback] 全局异常监听回调
     */
    fun setCommonErrorCallback(callback: ICommonErrorCallback): NetInit {
        commonErrorCallback = callback
        return this
    }


    // other

    /**
     * 设置数据解析器
     *
     * [dataParser] 数据解析器
     */
    fun dataParser(dataParser: IDataParser): NetInit {
        mDataParser = dataParser
        return this
    }
}