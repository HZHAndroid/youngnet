package com.young.net.utils

import android.util.Log
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.young.net.type_adapter.ObjectTypeAdapter
import java.lang.ref.WeakReference
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

/**
 * @author:  Young
 * .
 * @email:   1104090872@qq.com
 * .
 * @date :   2021/8/24 22:06 周二
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

 * @description: json 操作工具类
 *
 * Gson高级使用和GsonBuilder设置
 * https://www.jianshu.com/p/31396863d1aa
 *
 * Gson 基础教程 —— 自定义类型适配器(TypeAdapter)
 * https://blog.csdn.net/magicboylinw/article/details/8468554/
 *
 */
object JsonUtil {

    @JvmStatic
    private var mGson: WeakReference<Gson>? = null

    /**
     * 获取 Gson 对象
     */
    fun getGson(): Gson {
//        val gson = GsonBuilder()
//            .registerTypeAdapter(
//                object : TypeToken<TreeMap<String?, Any?>?>() {}.type,
//                JsonDeserializer<Any?> { json, typeOfT, context ->
//                    val treeMap: TreeMap<String?, Any?> = TreeMap()
//                    val jsonObject = json.asJsonObject
//                    val entrySet = jsonObject.entrySet()
//                    for ((key, ot) in entrySet) {
//                        if (ot is JsonPrimitive) {
//                            treeMap.put(key, (ot as JsonPrimitive).asString)
//                        } else {
//                            treeMap.put(key, ot)
//                        }
//                    }
//                    treeMap
//                }).create()
        if (mGson == null || mGson!!.get() == null) {
            // 完美解决gson将Integer默认转换成Double的问题 https://www.jb51.net/article/107464.htm
            // registerTypeAdapter 为某特定对象设置固定的序列和反序列方式，实现JsonSerializer和JsonDeserializer接口
//            val gson = registerMapJsonDeserializer(GsonBuilder())
//                .create()
            val gson = GsonBuilder().create()
            setObjectTypeAdapter(gson)
            mGson = WeakReference(gson)
        }
        return mGson!!.get()!!
    }

    /**
     * 设置 ObjectTypeAdapter
     *
     * 彻底解决 Gson 将 int 转换为 double 的问题 https://blog.csdn.net/s_156/article/details/116329783
     *
     * [gson] Gson 对象
     */
    private fun setObjectTypeAdapter(gson: Gson) {
        try {
            val factories = Gson::class.java.getDeclaredField("factories")
            factories.isAccessible = true
            val o = factories[gson]
            val declaredClasses = Collections::class.java.declaredClasses
            for (c in declaredClasses) {
                if ("java.util.Collections\$UnmodifiableList" == c.name) {
                    val listField = c.getDeclaredField("list")
                    listField.isAccessible = true
                    val list = listField[o] as MutableList<TypeAdapterFactory>
                    val i = list.indexOf(com.google.gson.internal.bind.ObjectTypeAdapter.FACTORY)
                    list[i] = ObjectTypeAdapter.FACTORY
                    break
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 将对象转为 json 字符串
     *
     * [obj] 需要转换的对象
     *
     * [@return] 转换好的 json 字符串
     */
    fun toJson(obj: Any): String {
        return getGson().toJson(obj)
    }

    /**
     * 解析数据（这种方式在 java 无法调用）
     *
     * 注意：这种方式拿到的 T 是被擦除过的，例如：传入的是 Map<String,Any> ，这里打印的是 Map，会导致 自定义的 Map 解析器无法识别
     * 不过这种方式，加多一个参数 type: TypeToken<T> = object : TypeToken<T>() {} 就可以解决了
     *
     * [jsonStr] json 字符串
     *
     * [@return] 解析好的数据
     */
    @Throws(JsonParseException::class)
    inline fun <reified T> parse(
        jsonStr: String
    ): T {
        try {
            val type: TypeToken<T> = object : TypeToken<T>() {}
            return getGson().fromJson(jsonStr, type.type)
        } catch (e: Exception) {
            throw JsonParseException("数据解析失败：${e.message}")
        }
    }

    /**
     * 解析数据（这种方式在 java 也可以调用）
     *
     * [jsonStr] json 字符串
     *
     * [type] 需要解析的数据类型
     *
     * [@return] 解析好的数据
     */
    @Throws(JsonParseException::class)
    fun <T> parse(jsonStr: String, type: TypeToken<T>): T {
        try {
            return getGson().fromJson(jsonStr, type.type)
        } catch (e: Exception) {
            throw JsonParseException("数据解析失败：${e.message}")
        }
    }

    /**
     * 解析数据（这种方式在 java 也可以调用）
     *
     * [jsonStr] json 字符串
     *
     * [type] 需要解析的数据类型
     *
     * [@return] 解析好的数据
     */
    @Throws(JsonParseException::class)
    fun <T> parse(jsonStr: String, type: Type): T {
        try {
            return getGson().fromJson(jsonStr, type)
        } catch (e: Exception) {
            throw JsonParseException("数据解析失败：${e.message}")
        }
    }


//    /**
//     * 注册 MapJsonDeserializer 数据解析为 Map 的解析器
//     *
//     * [builder] Gson 构建器
//     *
//     * [@return] 返回 Gson 构建器
//     *
//     */
//    private fun registerMapJsonDeserializer(builder: GsonBuilder): GsonBuilder {
//        // 因为这个注册器，必须要具体到某个类型才可以触发，所以 Map 有使用哪些类型就要注册哪些类型
//        val tArr = arrayOf(
//            object : TypeToken<Map<String, Any?>>() {},
//            object : TypeToken<TreeMap<String, Any?>>() {},
//            object : TypeToken<HashMap<String, Any?>>() {},
//        )
//        for (item in tArr) {
//            // 参数1：解析的类型，就是我们平时解析数据传入的最终解析结果类型
//            // 参数2：自定义的 Map 的解析器
//            Log.e("shenlong", "jsonStr item.type ${item.type}")
//            builder.registerTypeAdapter(
//                item.type, MapJsonDeserializer()
//            )
//        }
//        return builder
//    }
}

///**
// * 定义一个针对 Map 集合的 json 解析器
// * 解决 Gson转换map对象时 Integer类型自动转换成Double类型
// * https://www.cnblogs.com/lxk233/p/12696025.html
// */
//class MapJsonDeserializer : JsonDeserializer<Map<String, Any?>> {
//    @Throws(JsonParseException::class)
//    override fun deserialize(
//        json: JsonElement,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Map<String, Any?> {
//        // 定义一个 map
//        val map = HashMap<String, Any>()
//        // 将 json 转为 JsonObject 对象，并且获取所欲 json 字段和值的 Set 集合
//        val entrySet = json.asJsonObject.entrySet()
//        // 为 map 填充值
//        for ((key, value) in entrySet) {
//            map[key] = value
//        }
//        return map
//    }
//}