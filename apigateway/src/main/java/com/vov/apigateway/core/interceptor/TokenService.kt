package com.vinsmart.officeapp.apigateway.core.interceptor

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// File TokenService
// @project OfficeApp
// @author minhhoang on 24-03-2021
interface TokenService {
    @POST("iam/api/v0/refresh-token")
    fun refreshToken(
        @Body body: Map<String, String>
    ): Call<Map<String, String>>
}