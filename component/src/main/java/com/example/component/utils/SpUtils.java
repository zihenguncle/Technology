package com.example.component.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.component.GetContent;

public class SpUtils {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public SpUtils() {
        preferences = GetContent.getContent().getSharedPreferences("User", GetContent.getContent().MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * 向SP存入指定key对应的数据
     * 其中value可以是String、boolean、float、int、long等各种基本类型的值
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 清空SP里所以数据
     */
    public static void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 删除SP里指定key对应的数据项
     * @param key
     */
    public static void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    /**
     * 判断SP是否包含特定key的数据
     * @param key
     * @return
     */
    public static boolean contains(String key){
        return preferences.contains(key);
    }


}
