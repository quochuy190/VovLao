package com.huynq.vovlao.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.huynq.vovlao.data.model.*
import com.huynq.vovlao.data.repository.SongRepository
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


class HomeViewModel : BaseViewModel() {
    private var parentJob = Job()

    // By default all the coroutines launched in this scope should be using the Main dispatcher
    val apiClient = ApiClient.getClient()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    val songRepo  = SongRepository()
    val loadSong: MutableLiveData<List<Song>> = MutableLiveData()
    val mListProgram: MutableLiveData<List<Program>> = MutableLiveData()
    val mUser: User = SharedPrefs.instance.get(ConstantCommon.KEY_USER_NAME, User::class.java)
    val mLanguage: Language = LanguageUtils().getCurrentLanguage()!!
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun loadAllUser(lifecycleOwner: LifecycleOwner) {
        songRepo.loadStreaming().observe(lifecycleOwner, Observer {
            loadSong.postValue(it)
        })
        // resRoom.postValue()
    }
    fun insert(songs: List<Song>) = scope.launch(Dispatchers.IO) {
        songRepo.insertList(songs)
    }
    fun deleteStreaming() = scope.launch(Dispatchers.IO) {
        songRepo.deleteStreaming()
    }

    fun exeApiGetChannel() {
        apiClient.getChannels(mUser.userId, mLanguage.id+1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }.subscribe { t1: ApiResult<List<Chanel>>?, t2: Throwable? ->
                loading.postValue(false)
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        deleteStreaming()
                        val mListRoom: MutableList<Song> = ArrayList()
                        Thread.sleep(100)
                        for (song in t1.data!!){
                            mListRoom.add(Song(song.id, song.description, song.link, song.description, song.logo, "30000", 3))
                        }
                        insert(mListRoom)
                    }
                }else{
                    error.postValue(t2)
                }
             }
    }

    fun exeApiProgram(idChannel: Int) {
        apiClient.getProgram(mUser.userId, mLanguage.id+1, idChannel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
           // .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                error.postValue(it)
            }.subscribe { t1: ApiResult<List<Program>>?, t2: Throwable? ->
                if (t1!=null){
                    if (t1!!.errorCode ==200){
                        Timber.e(""+t1)
                        mListProgram.postValue(t1.data)
                    }
                }else{
                    error.postValue(t2)
                }
            }
    }
}