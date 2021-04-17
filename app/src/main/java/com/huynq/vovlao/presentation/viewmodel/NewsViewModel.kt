package com.huynq.vovlao.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.local.VovLaoDatabase
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.Epg
import com.huynq.vovlao.data.repository.UserRepository
import com.vbeeon.iotdbs.data.model.ApiResult
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class NewsViewModel : BaseViewModel() {
    private var parentJob = Job()

    // By default all the coroutines launched in this scope should be using the Main dispatcher
    val apiFloor1 = ApiClient.getClientFloor1()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit var repositoryUser: UserRepository
    val loadUserRes: MutableLiveData<List<UserEntity>> = MutableLiveData()
    init {
        Timber.e("init")
        val userchDao = VovLaoDatabase.getInstance(VoVApplication.instance)?.userDao()
        repositoryUser = UserRepository(userchDao!!)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun loadAllUser(lifecycleOwner: LifecycleOwner) {
        repositoryUser.loadAllUserByType(1).observe(lifecycleOwner, Observer {
            loadUserRes.postValue(it)
        })
        // resRoom.postValue()
    }

    fun insertUserAdmin(user : UserEntity) = scope.launch(Dispatchers.IO) {
        Timber.e(""+user.id)
        repositoryUser.insert(user)
    }
    fun update(user : UserEntity) = scope.launch(Dispatchers.IO) {
        Timber.e(""+user.id)
        repositoryUser.update(user)
    }

    fun deleteUser(user : UserEntity) = scope.launch(Dispatchers.IO) {
        Timber.e(""+user.id)
        repositoryUser.deleteUser(user)
    }

    fun exeGetNewCategory(idChannel: Int) {
//        apiClient.getProgram(mUser.userId, mLanguage.id+1, idChannel)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { loading.postValue(true) }
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError {
//                error.postValue(it)
//            }.subscribe { t1: ApiResult<List<Epg>>?, t2: Throwable? ->
//                if (t1!=null){
//                    if (t1!!.errorCode ==200){
//                        Timber.e(""+t1)
//                        mListProgram.postValue(t1.data)
//                    }
//                }else{
//                    error.postValue(t2)
//                }
//            }
    }
}