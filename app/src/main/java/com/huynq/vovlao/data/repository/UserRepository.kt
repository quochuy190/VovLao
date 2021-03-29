package com.huynq.vovlao.data.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.huynq.vovlao.data.local.dao.UserDao
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.User
import com.huynq.vovlao.data.remote.data.InitRequest
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.utils.observerOnDatabase
import io.reactivex.rxjava3.core.Observable


class UserRepository(val usertDao: UserDao) {
    val apiClient = ApiClient.getClient()

    fun loadScriptByRoomId(room_id: Int) :  LiveData<UserEntity>{
        return usertDao.loadUserById(room_id)
    }
    fun loadUserAndPass(user: String, pass: String) :  LiveData<List<UserEntity>>{
        return usertDao.getUserAndPass(user, pass)
    }

    fun loadAllUserByType(type :Int) :  LiveData<List<UserEntity>>{
        return usertDao.getUserbyType(type)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(obj: UserEntity) {
        usertDao.delete(obj.id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(obj: UserEntity) {
        usertDao.insertUserObj(obj)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(obj: UserEntity) {
        usertDao.updatetoDao(obj)
    }

}