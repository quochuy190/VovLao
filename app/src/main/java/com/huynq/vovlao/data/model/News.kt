package com.huynq.vovlao.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    val id: Int,
    val newsCategoryId: Int,
    val newsId: Int,
    @SerializedName("newsName")
    val newsName: String,
    val title: String,
    @SerializedName("image")
    val urlCover: String,
    val sortDes: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: String,
    @SerializedName("createTime")
    val dateTime: String
) : Serializable