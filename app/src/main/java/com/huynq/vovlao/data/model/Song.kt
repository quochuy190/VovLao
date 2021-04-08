package com.huynq.vovlao.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.player.model.ASong
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "song")
@Parcelize
data class Song(
    @PrimaryKey
    var id: Int,
    var songName: String?,
    var path: String,
    var artistName: String?,
    var albumArt: String?,
    var duration: String?,
    var type: Int = 0,
    var isSelected : Boolean = false
) : ASong(id, songName, albumArt, artistName, path, type, duration), Parcelable