package com.zteng.common.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import java.util.ArrayList
import java.util.HashMap

object SharedPreferencesUtil {

    private var sp: SharedPreferences? = null
    /**
     * 初始化SharedPreferencesUtil,只需要初始化一次，建议在Application中初始化
     *
     * @param context 上下文对象
     * @param name    SharedPreferences Name
     */
    fun getInstance(context: Context, name: String) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    /**
     * 保存数据到SharedPreferences
     *
     * @param key   键
     * @param value 需要保存的数据
     * @return 保存结果
     */
    fun putData(key: String, value: Any): Boolean {
        var result: Boolean
        val editor = sp!!.edit()
        val type = value.javaClass.simpleName
        try {
            when (type) {
                "Boolean" -> editor.putBoolean(key, value as Boolean)
                "Long" -> editor.putLong(key, value as Long)
                "Float" -> editor.putFloat(key, value as Float)
                "String" -> editor.putString(key, value as String)
                "Integer" -> editor.putInt(key, value as Int)
                else -> {
                    val gson = Gson()
                    val json = gson.toJson(value)
                    editor.putString(key, json)
                }
            }
            result = true
        } catch (e: Exception) {
            result = false
            e.printStackTrace()
        }

        editor.apply()
        return result
    }

    /**
     * 获取SharedPreferences中保存的数据
     *
     * @param key          键
     * @param defaultValue 获取失败默认值
     * @return 从SharedPreferences读取的数据
     */
    fun getData(key: String, defaultValue: Any): Any? {
        var result: Any?
        val type = defaultValue.javaClass.simpleName
        try {
            when (type) {
                "Boolean" -> result = sp!!.getBoolean(key, defaultValue as Boolean)
                "Long" -> result = sp!!.getLong(key, defaultValue as Long)
                "Float" -> result = sp!!.getFloat(key, defaultValue as Float)
                "String" -> result = sp!!.getString(key, defaultValue as String)
                "Integer" -> result = sp!!.getInt(key, defaultValue as Int)
                else -> {
                    val gson = Gson()
                    val json = sp!!.getString(key, "")
                    if (json != "" && json!!.length > 0) {
                        result = gson.fromJson(json, defaultValue.javaClass)
                    } else {
                        result = defaultValue
                    }
                }
            }
        } catch (e: Exception) {
            result = null
            e.printStackTrace()
        }

        return result
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    fun <T : Any> putListData(key: String, list: List<T>): Boolean {
        var result: Boolean
        val type = list[0].javaClass.getSimpleName()
        val editor = sp!!.edit()
        val array = JsonArray()
        try {
            when (type) {
                "Boolean" -> for (i in list.indices) {
                    array.add(list[i] as Boolean)
                }
                "Long" -> for (i in list.indices) {
                    array.add(list[i] as Long)
                }
                "Float" -> for (i in list.indices) {
                    array.add(list[i] as Float)
                }
                "String" -> for (i in list.indices) {
                    array.add(list[i] as String)
                }
                "Integer" -> for (i in list.indices) {
                    array.add(list[i] as Int)
                }
                else -> {
                    val gson = Gson()
                    for (i in list.indices) {
                        val obj = gson.toJsonTree(list[i])
                        array.add(obj)
                    }
                }
            }
            editor.putString(key, array.toString())
            result = true
        } catch (e: Exception) {
            result = false
            e.printStackTrace()
        }

        editor.apply()
        return result
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    fun <T> getListData(key: String, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        val json = sp!!.getString(key, "")
        if (json != "" && json!!.length > 0) {
            val gson = Gson()
            val array = JsonParser().parse(json).getAsJsonArray()
            for (elem in array) {
                list.add(gson.fromJson(elem, cls))
            }
        }
        return list
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    fun <K, V> putHashMapData(key: String, map: Map<K, V>): Boolean {
        var result: Boolean
        val editor = sp!!.edit()
        try {
            val gson = Gson()
            val json = gson.toJson(map)
            editor.putString(key, json)
            result = true
        } catch (e: Exception) {
            result = false
            e.printStackTrace()
        }

        editor.apply()
        return result
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param clsV
     * @return HashMap
     */
    fun <V> getHashMapData(key: String, clsV: Class<V>): HashMap<String, V> {
        val json = sp!!.getString(key, "")
        val map = HashMap<String, V>()
        val gson = Gson()
        val obj = JsonParser().parse(json).getAsJsonObject()
        val entrySet = obj.entrySet()
        for (entry in entrySet) {
            val entryKey = entry.key
            val value = entry.value as JsonObject
            map[entryKey] = gson.fromJson(value, clsV)
        }
        Log.e("SharedPreferencesUtil", obj.toString())
        return map
    }

    fun clear(context: Context, key: String) {
        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

}
