package com.young.net.parse

import com.google.gson.JsonParseException
import java.lang.reflect.Type

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/24 22:00 周二
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

 * @description: 数据解析接口
 */
abstract class IDataParser {

    /**
     * 数据解析
     *
     * [jsonStr] json 字符串
     *
     * [dataType] 需要解析的数据类型
     *
     * [@return] 解析好的数据
     */
    @Throws(JsonParseException::class)
    abstract fun <T> parseJson(jsonStr: String, dataType: Type): T
}