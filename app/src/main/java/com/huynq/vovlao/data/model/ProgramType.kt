package com.huynq.vovlao.data.model


data class ProgramType(
    val id: Int,
    val typeName: String,
    var typeImage: String,
    var isSelected : Boolean = false
)