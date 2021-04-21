package com.huynq.vovlao.data.model

import com.google.gson.annotations.SerializedName

data class Program(
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
    val programImage: String,
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("proramTypeId")
    val proramTypeId: String,
    @SerializedName("typeName")
    val typeName: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("totalPlay")
    val totalPlay: Int,
    var isSelected :Boolean = false
)