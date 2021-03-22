package com.huynq.vovlao.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.local.VovLaoDatabase
import com.huynq.vovlao.data.local.entity.UserEntity
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

    fun exeApi(lifecycleOwner: LifecycleOwner, pass: String) {
//        val request = LoginSuperVisorRequest();
//        Timber.e("call loginRemote")
//        request.password = "fd6aa3889d2415bcbc13803f303f1137"
//        request.uuid = "e92947d6-2dcf-3375-b3c8-7789f969de6a"
//
//        val requestLogin = LoginRequest();
//        Timber.e("call loginRemote")
//        requestLogin.password = "fd6aa3889d2415bcbc13803f303f1137"
//        requestLogin.username = "e92947d6-2dcf-3375-b3c8-7789f969de6a"
//        apiFloor1.loginSupervisor(request)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .flatMap({ it -> apiFloor2.login(requestLogin) })
//            .subscribe { t1: ApiResult<LoginEntity>?, t2: Throwable? ->
//                loading.postValue(false)
//            }
    }
}