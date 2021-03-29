package com.vbeeon.iotdbs.data.remote

import com.huynq.vovlao.data.remote.HttpHeaderInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL_FLOOR_1 = "http://10.1.124.172:9999"
const val BASE_URL = "http://123.30.180.58:3000"
const val API_KEY = "a798e50518a1f88b16c46de26b60104370a3a3c5f273426feddf80c4306cf41e"

object ApiClient {
    private const val REQUEST_TIMEOUT = 30
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())
          //  .addInterceptor(ApiExceptionInterceptor())
            .addInterceptor( HttpHeaderInterceptor())
            .build()

    fun getClientFloor1(): ApiInterface {

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL_FLOOR_1)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
    }
    fun getClient(): ApiInterface {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}