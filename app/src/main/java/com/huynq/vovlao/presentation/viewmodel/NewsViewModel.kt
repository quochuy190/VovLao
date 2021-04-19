package com.huynq.vovlao.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.huynq.vovlao.VoVApplication
import com.huynq.vovlao.data.local.VovLaoDatabase
import com.huynq.vovlao.data.local.entity.UserEntity
import com.huynq.vovlao.data.model.*
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
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import kotlin.coroutines.CoroutineContext


class NewsViewModel : BaseViewModel() {
    private var parentJob = Job()
    // khoi tao api
    val apiClient = ApiClient.getClient()
    val mUser: User = SharedPrefs.instance.get(ConstantCommon.KEY_USER_NAME, User::class.java)
    val mLanguage: Language = LanguageUtils().getCurrentLanguage()!!
    //
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    val loadNewsCategory: MutableLiveData<List<NewCategory>> = MutableLiveData()
    val loadNews: MutableLiveData<List<News>> = MutableLiveData()
    val loadNewDetail: MutableLiveData<List<News>> = MutableLiveData()
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun exeGetNewCategory() {
        apiClient.getNewsCategory(mUser.userId, mLanguage.id+1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }.subscribe { t1: ApiResult<List<NewCategory>>?, t2: Throwable? ->
                loading.postValue(false)
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        Timber.e(""+t1)
                        loadNewsCategory.postValue(t1.data)
                    }
                }else{
                    error.postValue(t2)
                }
            }
    }

    fun exeGetNews(category: NewCategory) {
        apiClient.getNewsByCategory(mUser.userId, category.id, mLanguage.id+1, 30, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }.subscribe { t1: ApiResult<List<News>>?, t2: Throwable? ->
                loading.postValue(false)
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        Timber.e(""+t1)
                        loadNews.postValue(t1.data)
                    }
                }else{
                    error.postValue(t2)
                }
            }
    }

    fun exeGetNewsDetail(new: News) {
        apiClient.getNewsDetail(mUser.userId, mLanguage.id+1, new.newsId, new.newsCategoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }.subscribe { t1: ApiResult<List<News>>?, t2: Throwable? ->
                loading.postValue(false)
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        Timber.e(""+t1)
                        loadNewDetail.postValue(t1.data)
                    }
                }else{
                    error.postValue(t2)
                }
            }
    }
}