package com.huynq.vovlao.data.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.local.VovLaoDatabase
import com.huynq.vovlao.data.local.dao.UserDao
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.data.model.User
import com.huynq.vovlao.data.remote.data.InitRequest
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.utils.observerOnDatabase
import io.reactivex.rxjava3.core.Observable


class SongRepository() {
    val apiClient = ApiClient.getClient()
    val songDao = VovLaoDatabase.getInstance(VoVApplication.instance)!!.songDao()
    fun loadStreaming() :  LiveData<List<Song>>{
        return songDao.loadListStreaming()
    }

//    fun loadSongByType(type :Int) :  LiveData<List<Song>>{
//        return songDao.loadSongByType(type)
//    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteStreaming() {
        songDao.deleteStreaming()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: Song) {
        songDao.insert(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertList(list: List<Song>) {
        songDao.insertList(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: Song) {
        songDao.updatetoDao(obj)
    }

}