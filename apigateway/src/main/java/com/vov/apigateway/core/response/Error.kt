package com.vinsmart.officeapp.apigateway.core.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// File Error
// @project OfficeApp
// @author minhhoang on 18-03-2021
data class Error(
    @SerializedName("field")
    @Expose
    val field: Any? = null,

    @SerializedName("objectName")
    @Expose
    val objectName: Any? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null
)