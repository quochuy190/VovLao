package com.huynq.vovlao.data.model

import com.google.gson.annotations.SerializedName

data class Epg(
    val id: Int,
    @SerializedName("programName")
    val title: String,
    @SerializedName("programDes")
    val description: String,
    @SerializedName("startTime")
    val timeStart: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("programLink")
    val programLink: String,
    @SerializedName("programImage")
    val programImage: String
)