package com.vinsmart.officeapp.apigateway.core.interceptor

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.vinsmart.commons.repository.prefer.TechnoParkSharedPreferences
import com.vinsmart.commons.utils.CommonUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

// File HeaderInterceptor
// @project OfficeApp
// @author minhhoang on 19-03-2021
class HeaderInterceptor(
    private val context: Context,
    private val sharedPreferences: TechnoParkSharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("X-HTTP-UID", CommonUtils.getDeviceSerialNumber(context))
        requestBuilder.addHeader("X-HTTP-DEVICE-MODEL", Build.MODEL)
        requestBuilder.addHeader("X-HTTP-ANDROID-VERSION", Build.VERSION.SDK_INT.toString())
        try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            requestBuilder.addHeader("X-HTTP-APP-VERSION", version)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        requestBuilder.addHeader("X-HTTP-LANG", Locale.getDefault().language)
        val accessToken = sharedPreferences.accessToken
        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", accessToken)
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}