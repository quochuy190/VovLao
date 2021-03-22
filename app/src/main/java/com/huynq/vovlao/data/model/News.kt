package com.huynq.vovlao.data.model

import java.io.Serializable

data class News(val id: Int, val title: String, val urlCover: String, val sortDes : String, val description: String, val dateTime: String) : Serializable