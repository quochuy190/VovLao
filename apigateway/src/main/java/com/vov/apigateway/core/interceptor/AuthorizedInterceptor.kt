package com.vinsmart.officeapp.apigateway.core.interceptor

import com.vinsmart.commons.repository.prefer.TechnoParkSharedPreferences
import com.vinsmart.officeapp.apigateway.ApiGateWay
import com.vinsmart.officeapp.apigateway.core.event.AuthorizedEvent.Companion.instance
import com.vinsmart.officeapp.apigateway.core.event.AuthorizedEventType
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

// File AuthorizedInterceptor
// @project OfficeApp
// @author minhhoang on 19-03-2021
class AuthorizedInterceptor(private val sharedPreferences: TechnoParkSharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        val authorizedEvent = instance
        if (response.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            val refreshTokenOld = sharedPreferences.refreshToken
            if (refreshTokenOld != null) {
                val tokenService = ApiGateWay.getInstance().createService(TokenService::class.java)
                val body = mapOf(
                    "refreshToken" to refreshTokenOld
                )
                try {
                    sharedPreferences.reset()
                    val tokenResult =
                        tokenService.refreshToken(body).execute()
                            .body()
                    val accessToken = tokenResult?.get("access_token")
                    val tokenType = tokenResult?.get("token_type")
                    val refreshToken = tokenResult?.get("refresh_token")
                    sharedPreferences.setAccessToken(accessToken, tokenType)
                    sharedPreferences.refreshToken = refreshToken
                    val requestAuth = original.newBuilder()
                        .addHeader("Authorization", sharedPreferences.accessToken)
                        .build()
                    return chain.proceed(requestAuth)
                } catch (e: IOException) {

                }
            }
            sharedPreferences.reset()
            authorizedEvent.setAuthorized(AuthorizedEventType.UN_AUTHORIZED)
            return response
        } else authorizedEvent.setAuthorized(AuthorizedEventType.AUTHORIZED)
        return response
    }
}