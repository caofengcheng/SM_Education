package com.zteng.mvp.base

import android.net.ParseException
import android.text.TextUtils

import org.json.JSONException
import org.slf4j.LoggerFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

internal object HttpExceptionUtil {
    private val logger = LoggerFactory.getLogger(HttpExceptionUtil::class.java)

    fun exceptionHandler(e: Throwable): String {
        val errorMsg: String
        if (e is UnknownHostException) {
            errorMsg = "网络不可用"
        } else if (e is SocketTimeoutException) {
            errorMsg = "请求网络超时"
        } else if (e is ConnectException) {
            errorMsg = "连接失败"
        } else if (e is HttpException) {
            errorMsg = convertStatusCode(e)
        } else if (e is ParseException || e is JSONException) {
            errorMsg = "数据解析错误"
        } else if (e is NullPointerException) {
            errorMsg = "获取数据失败"
        } else {
            errorMsg = if (TextUtils.isEmpty(e.message)) e.toString() else e.message.toString()
        }
        logger.debug(errorMsg)
        return errorMsg
    }

    private fun convertStatusCode(httpException: HttpException): String {
        var msg: String
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错"
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求"
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面"
        } else {
            msg = httpException.message()
        }
        return msg
    }
}