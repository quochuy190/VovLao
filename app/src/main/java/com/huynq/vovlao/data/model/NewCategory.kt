package com.huynq.vovlao.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewCategory(
    val id: Int,
    @SerializedName("name")
    val name: String
): Serializable