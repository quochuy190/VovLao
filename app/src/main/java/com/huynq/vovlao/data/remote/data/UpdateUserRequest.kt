package com.huynq.vovlao.data.remote.data

data class UpdateUserRequest(val userId: String, val deviceType: Int, val versionOs: String, val tokenKey:String, val appVersion: String, val languageId: String)