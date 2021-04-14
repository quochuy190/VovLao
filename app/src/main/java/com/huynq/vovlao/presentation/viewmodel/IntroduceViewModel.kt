package com.huynq.vovlao.presentation.viewmodel

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.huynq.vovlao.BuildConfig
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.local.VovLaoDatabase
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.User
import com.huynq.vovlao.data.remote.data.InitRequest
import com.huynq.vovlao.data.repository.UserRepository
import com.huynq.vovlao.utils.LanguageUtils
import com.huynq.vovlao.utils.SharedPrefs
import com.vbeeon.iotdbs.data.model.ApiResult
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import kotlin.coroutines.CoroutineContext


class IntroduceViewModel : BaseViewModel() {
    private var parentJob = Job()

    // By default all the coroutines launched in this scope should be using the Main dispatcher
    val apiClient = ApiClient.getClient()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit var repositoryUser: UserRepository
    val loadInit: MutableLiveData<Boolean> = MutableLiveData()
    init {
        Timber.e("init")
        val userchDao = VovLaoDatabase.getInstance(VoVApplication.instance)?.userDao()
        repositoryUser = UserRepository(userchDao!!)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun exeApi(lifecycleOwner: LifecycleOwner, uuid: String, lanCode: Int) {
        val request = InitRequest(uuid, 2, ""+Build.VERSION.SDK_INT, "abctest", ""+ BuildConfig.VERSION_NAME,""+lanCode);
        apiClient.apiInit(request        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ApiResult<User>?, t2: Throwable? ->
                loading.postValue(false)
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        LanguageUtils().loadLocale()
                        SharedPrefs.instance.put(ConstantCommon.KEY_USER_NAME, t1.data)
                        loadInit.postValue(true)
                    }
                }else{
                    error.postValue(t2)
                }

            }
    }
}