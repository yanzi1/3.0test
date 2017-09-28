package com.me.fanyin.zbme.views.course.play.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

	private static final String TAG = ReflectionUtils.class.getName();

	public static Object getFieldValue(Class<?> fieldClass, String fieldName, Object instance) {
		try {
			final Field field = fieldClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(instance);
		} catch (Exception e) {
			Log.w(TAG, Log.getStackTraceString(e));
		}
		return null;
	}
	
	public static void setFieldValue(Class<?> fieldClass, String fieldName, Object instance, Object value) {
		try {
			final Field field = fieldClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		} catch (Exception e) {
			Log.w(TAG, Log.getStackTraceString(e));
		}
	}

	public static Object invokeMethod(Class<?> methodClass, String methodName, Class<?>[] parameters, Object instance, Object... arguments) {
		try {
			final Method method = methodClass.getDeclaredMethod(methodName, parameters);
			method.setAccessible(true);
			return method.invoke(instance, arguments);
		} catch (Exception e) {
			Log.w(TAG, Log.getStackTraceString(e));
		}
		return null;
	}
}
