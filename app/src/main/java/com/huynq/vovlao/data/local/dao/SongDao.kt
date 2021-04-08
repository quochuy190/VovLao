package com.huynq.vovlao.data.local.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.huynq.vovlao.data.model.Song


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<Song>)

    @Query("SELECT * FROM song WHERE type =3")
    fun loadListStreaming(): LiveData<List<Song>>

//    @Query("SELECT * FROM song WHERE type =type")
//    fun loadSongByType(type: Int): LiveData<List<Song>>

    @Query("SELECT * FROM song WHERE id IN (:id)")
    fun loadSongById(id: Int): LiveData<Song>

    @Query("DELETE FROM song WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM song WHERE id = 3")
    fun deleteStreaming()

    @Query("DELETE FROM song")
    fun deleteAll()

    @Update
    fun updatetoDao(user: Song?)


}