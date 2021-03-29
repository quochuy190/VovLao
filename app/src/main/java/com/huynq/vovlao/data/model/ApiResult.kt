package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName

class ApiResult <T>{
    @SerializedName("statusCode")
    var errorCode = 0

    @SerializedName("message")
    var errorMessage: String? = null

    @SerializedName("data")
    var data: T? = null


}