package com.android.player

import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.player.SongPlayerViewModel.Companion.getPlayerViewModelInstance
import com.android.player.model.ASong
import com.android.player.service.OnPlayerServiceCallback
import com.android.player.service.SongPlayerService
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Language
import com.huynq.vovlao.utils.SharedPrefs
import com.vbeeon.iotdbs.data.model.MessageEventBus
import kotlinx.android.synthetic.main.fragment_replay.view.*
import org.greenrobot.eventbus.EventBus
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.*
import kotlin.collections.ArrayList


open class BaseSongPlayerActivity : AppCompatActivity(), OnPlayerServiceCallback {

    lateinit var progressDialog: ProgressDialog
    private var mService: SongPlayerService? = null
    private var mBound = false
    private var mSong: ASong? = null
    private var mSongList: MutableList<ASong>? = null
    private var msg = 0
    val songPlayerViewModel: SongPlayerViewModel = getPlayerViewModelInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(this.getString(R.string.loading))
        progressDialog.setCancelable(false)
    }
    fun showProgress() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    fun dismissProgress() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                ACTION_PLAY_SONG_IN_LIST -> mService?.play(mSongList, mSong)
                ACTION_PAUSE -> mService?.pause()
                ACTION_STOP -> {
                    mService?.stop()
                    songPlayerViewModel.stop()
                }
            }
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to SongPlayerService, cast the IBinder and get SongPlayerService instance
            val binder = service as SongPlayerService.LocalBinder
            mService = binder.service
            mBound = true
            mService?.subscribeToSongPlayerUpdates()
            mHandler.sendEmptyMessage(msg)
            mService?.addListener(this@BaseSongPlayerActivity)
        }

        override fun onServiceDisconnected(classname: ComponentName) {
            mBound = false
            mService?.removeListener()
            mService = null
        }
    }

    private fun bindPlayerService() {
        if (!mBound) bindService(Intent(this, SongPlayerService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }


    fun play(songList: MutableList<ASong>?, song: ASong) {
        msg = ACTION_PLAY_SONG_IN_LIST
        mSong = song
        mSongList = songList
        songPlayerViewModel.setPlayStatus(true)
        if (mService == null) bindPlayerService()
        else mHandler.sendEmptyMessage(msg)
    }

    private fun pause() {
        msg = ACTION_PAUSE
        songPlayerViewModel.setPlayStatus(false)
        if (mService == null) bindPlayerService()
        else mHandler.sendEmptyMessage(msg)
    }

    fun stop() {
        msg = ACTION_STOP
        songPlayerViewModel.setPlayStatus(false)
        if (mService == null) bindPlayerService()
        else mHandler.sendEmptyMessage(msg)
    }

    fun next() {
        mService?.skipToNext()
    }

    fun previous() {
        mService?.skipToPrevious()
    }

    fun toggle() {
        if (songPlayerViewModel.isPlayData.value == true) pause()
        else songPlayerViewModel.playerData.value?.let { it1 -> play(mSongList, it1) }
    }

    fun seekTo(position: Long?) {
        position?.let { nonNullPosition ->
            songPlayerViewModel.seekTo(nonNullPosition)
            mService?.seekTo(nonNullPosition)
        }
    }

    fun addNewPlaylistToCurrent(songList: ArrayList<ASong>) {
        mService?.addNewPlaylistToCurrent(songList)
    }

    fun shuffle() {
        mService?.onShuffle(songPlayerViewModel.isShuffleData.value ?: false)
        songPlayerViewModel.shuffle()
    }

    fun repeatAll() {
        mService?.onRepeatAll(songPlayerViewModel.isRepeatAllData.value ?: false)
        songPlayerViewModel.repeatAll()
    }

    fun repeat() {
        mService?.onRepeat(songPlayerViewModel.isRepeatData.value ?: false)
        songPlayerViewModel.repeat()
    }

    override fun updateSongData(song: ASong) {
        songPlayerViewModel.updateSong(song)
    }

    override fun setPlayStatus(isPlay: Boolean) {
        if (isPlay)
            EventBus.getDefault().post(MessageEventBus(1, "", null))
        songPlayerViewModel.setPlayStatus(isPlay)
       // EventBus.getDefault().post(MessageEvent())
    }

    override fun updateSongProgress(duration: Long, position: Long) {
        songPlayerViewModel.setChangePosition(position, duration)
    }

    override fun setBufferingData(isBuffering: Boolean) {
        Log.d(TAG, "setBufferingData: " + isBuffering)
        songPlayerViewModel.setBuffering(isBuffering)
    }

    override fun setVisibilityData(isVisibility: Boolean) {
        Log.d(TAG, "setVisibilityData: " + isVisibility)
        songPlayerViewModel.setVisibility(isVisibility)
    }

    private fun unbindService(){
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }

    override fun stopService(){
        unbindService()
        mService = null
    }

    override fun onDestroy() {
        stopService()
        super.onDestroy()
    }


    companion object {
        private val TAG = BaseSongPlayerActivity::class.java.name
        const val SONG_LIST_KEY = "SONG_LIST_KEY"
        private const val ACTION_PLAY_SONG_IN_LIST = 1
        private const val ACTION_PAUSE = 2
        private const val ACTION_STOP = 3
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val config = Configuration()
        applyOverrideConfiguration(config)
    }

    override fun applyOverrideConfiguration(newConfig: Configuration) {
        super.applyOverrideConfiguration(updateConfigurationIfSupported(newConfig))
    }

    open fun updateConfigurationIfSupported(config: Configuration): Configuration? {
        // Configuration.getLocales is added after 24 and Configuration.locale is deprecated in 24
        if (Build.VERSION.SDK_INT >= 24) {
            if (!config.locales.isEmpty) {
                return config
            }
        } else {
            if (config.locale != null) {
                return config
            }
        }
        // Please Get your language code from some storage like shared preferences
        val languageCode = SharedPrefs.instance.get(ConstantCommon.LANGUAGE, Language::class.java).code
        val locale = Locale(languageCode)
        if (locale != null) {
            // Configuration.setLocale is added after 17 and Configuration.locale is deprecated
            // after 24
            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }
        }
        return config
    }
}