package com.huynq.vovlao.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.huynq.vovlao.data.local.dao.SongDao
import com.huynq.vovlao.data.local.dao.UserDao
import com.huynq.vovlao.data.local.entity.StringListConverter
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.Song


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-January-2021
 * Time: 23:30
 * Version: 1.0
 */
@Database(entities = arrayOf(UserEntity::class, Song::class), version = 1)
@TypeConverters(StringListConverter::class)
abstract class VovLaoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun songDao(): SongDao

    companion object {
        private var INSTANCE: VovLaoDatabase? = null
        fun getInstance(context: Context): VovLaoDatabase? {
            if (INSTANCE == null) {
                synchronized(VovLaoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VovLaoDatabase::class.java, "VovLaoDatabase.db")
                            .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}