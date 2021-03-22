package com.vbeeon.iotdbs.data.model

import com.google.gson.annotations.SerializedName
import com.huynq.vovlao.data.model.Song

data class MessageEventBus( val style : Int =0,
                            var message: String? = null,
                            var data: Song? = null)