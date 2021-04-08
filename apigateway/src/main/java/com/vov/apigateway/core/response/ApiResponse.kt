package com.vinsmart.officeapp.apigateway.core.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// File Response
// @project OfficeApp
// @author minhhoang on 18-03-2021
data class ApiResponse<T>(
    @SerializedName("code")
    @Expose
    val code: Int? = null,

    @SerializedName("data")
    @Expose
    val data: T? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null,

    @SerializedName("error")
    @Expose
    val error: String? = null,

    @SerializedName("errors")
    @Expose
    val errors: List<Error>? = null
)