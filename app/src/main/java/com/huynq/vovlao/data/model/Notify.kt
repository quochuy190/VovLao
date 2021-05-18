package com.huynq.vovlao.data.model

import java.io.Serializable

data class Notify(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val state: Int,
    val responseMessage: String,
    val sentTime: String
) : Serializable