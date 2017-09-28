package com.me.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jjr on 2017/6/1.
 *
 * 私有方法调用、私有成员变量获取工具类
 *
 * 利用java反射调用类的的私有方法
 */

public class PrivateUtils extends BaseUtils {

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz 目标类
     * @param methodName 方法名
     * @param classes 方法参数类型数组
     * @return 方法对象
     * @throws Exception
     */
    public static Method getMethod(Class clazz, String methodName, final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName, classes);
                }
            }
        }
        return method;
    }

    /**
     *
     * @param obj 调整方法的对象
     * @param methodName 方法名
     * @param classes 参数类型数组
     * @param objects 参数数组
     * @return 方法的返回值
     */
    public static Object invoke(final Object obj, final String methodName, final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            method.setAccessible(true);// 调用private方法的关键一句话
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(final Object obj, final String methodName, final Class[] classes) {
        return invoke(obj, methodName, classes, new Object[] {});
    }

    public static Object invoke(final Object obj, final String methodName) {
        return invoke(obj, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * 获取类的属性
     *
     * @param clazz 目标所在类的字节码
     * @param fieldName 私有属性名
     * @param t object from which the represented field's value is to be extracted
     * @return
     */
    public static <T> T getField(Class clazz,String fieldName,T t) {
        try {
            Field field = clazz.getDeclaredField(fieldName);//根据属性名称，获得类的属性成员Field
            field.setAccessible(true);//设置为可访问状态
            return (T) field.get(t);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
