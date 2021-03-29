package com.huynq.vovlao.data.model

data class User(
    val id: Int,
    val uuid: String,
    val deviceType: Int,
    val versionOs: String,
    val tokenKey:
    String,
    val appVersion: String,
    val userId: String,
    val state: Int,
    val languageId: Int,
    val updateTime: String,
    val createTime: String
)