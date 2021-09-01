package com.young.net.utils

import android.util.Log
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

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

 * @description: 反射工具类
 *
 * Android kotlin网络请求框架fuel（简单方便的使用，提供项目中使用实例）
 * https://blog.csdn.net/quan648997767/article/details/86537930
 *
 * 如何获得具体类型的通用接口
 * https://qa.1r1g.com/sf/ask/227294091/
 */
object ReflectUtil {
//    /**
//     * 通过反射获取泛型类型（这个是动脑学院 dnplayer 的，在kotlin没什么用）
//     *
//     * [obj] 需要反射的对象
//     */
//    fun analysisClassInfo(obj: Any): Class<*> {
//        // 在java中T.getClass() 或 T.class都是不合法的，因为T是泛型变量。
//        // 由于一个类的类型在编译期已确定，故不能在运行期得到T的实际类型。
//        // getGenericSuperclass：获取当前运行类泛型父类类型，即为参数化类型，有所有类型公用的高级接口Type接收。
//        // Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
//        val genType = obj.javaClass.genericSuperclass
//        Log.i("zee", "analysisClassInfo: genType=$genType")
//        // ParameterizedType参数化类型，即泛型
//        val pType = genType as ParameterizedType
//        // getActualTypeArguments获取参数化类型的数组，泛型可能有多个
//        val params = pType.actualTypeArguments
//        val type0 = params[0]
//        Log.i("zee", "analysisClassInfo: params=$type0")
//        return type0 as Class<*>
//    }

    /**
     * 校验是否泛型传递（因为泛型传递，会导致解析的时候无法反射具体的类型）
     *
     * 例如：class A<T> : B<T>{} ， 这里的泛型 T 会从子类传递到父类，导致运行时无法反射具体的类型，
     * 也就是泛型写什么就是返回什么（这里是 T）
     *
     * [type] 反射的泛型类型
     *
     * [@return] true: 泛型传递 false：非泛型传递
     */
    fun checkGenericTransitivity(type: Type): Boolean {
        try {
            if (type.toString().contains(".")) {
                // 非泛型传递
                // 非泛型传递的，则会获取有包名的具体类，例如：java.lang.Class
            } else {
                // 泛型传递
                // 因为泛型传递都是完整的单词，不会出现 . ，所以就用这个来做依据，例如：class A<T> : B<T>{}
                // 假如是泛型传递的话，这里得到的就是 T
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 通过抽象类获取泛型
     *
     * [cls] 抽象类/抽象类子类的 Class 对象
     *
     * [@return] 泛型的类型
     */
    fun getGenericByAbstract(cls: Class<*>): Type {
        val superclass = cls.genericSuperclass
        val parameterizedType = superclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        return actualTypeArguments[0]
    }

    /**
     * 通过抽象类获取泛型
     *
     * [obj] 抽象类/抽象类子类
     *
     * [@return] 泛型的类型
     */
    fun getGenericByAbstract(obj: Any): Type {
        return getGenericByAbstract(obj::class.java)
    }

    /**
     * 通过接口获取泛型
     *
     * [cls] 接口类/接口类子类的  Class 对象
     *
     * [@return] 泛型的类型
     */
    fun getGenericByInterface(cls: Class<*>): Type {
        val interfaces = cls.genericInterfaces
        val parameterizedType = interfaces[0] as ParameterizedType
        val typeArguments = parameterizedType.actualTypeArguments
        return typeArguments[0]
    }

    /**
     * 通过接口获取泛型
     *
     * [obj] 接口类/接口类子类
     *
     * [@return] 泛型的类型
     */
    fun getGenericByInterface(obj: Any): Type {
        return getGenericByInterface(obj::class.java)
    }
}