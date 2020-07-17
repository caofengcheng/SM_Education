package com.zteng.mvp

import com.zteng.mvp.api.RestApi
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.hazz.kotlinmvp.net.RetrofitManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 *
 * @author caofengcheng on 2020-01-13
 */
object RestManager {
    private var restApi: RestApi? = null

    fun getRestApi(): RestApi? {
        return restApi
    }

    fun initRest(url: String) {
        val mapper = ObjectMapper()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetrofitManager())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cookieJar(object : CookieJar {
                private var session: List<Cookie>? = null

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    session = cookies
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return if (session == null) ArrayList() else session!!
                }
            })
            .build()

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                //                    .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                .client(okHttpClient)
                .build()
            restApi = retrofit.create(RestApi::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}