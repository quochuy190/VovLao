package com.huynq.vovlao.presentation.fragment.main

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.player.SongPlayerViewModel
import com.android.player.model.ASong
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Epg
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.data.model.User
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.EPGAdapter
import com.huynq.vovlao.presentation.adapter.ImagesAdapter
import com.huynq.vovlao.presentation.adapter.RadioStreaminAdapter
import com.huynq.vovlao.presentation.viewmodel.HomeViewModel
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.data.model.MessageEventBus
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager
import timber.log.Timber


@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {
    val mSongList: MutableList<Song> = ArrayList()
    val mEpgList: MutableList<Epg> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var radioAdapter: RadioStreaminAdapter
    lateinit var epgAdapter: EPGAdapter
    private var mASongList: MutableList<ASong>? = mutableListOf()
    private var isMute = false
    val songPlayerViewModel: SongPlayerViewModel = SongPlayerViewModel.getPlayerViewModelInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
           // args.putBoolean("isChangeLanguage", isChange)
            fragment.setArguments(args)
            return fragment
        }
    }

    var isChange = false
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        arguments?.getBoolean("isChangeLanguage")?.let {
//            isChange = it
//        }
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEventBus?) {
        if (event!=null){
            when(event.style){
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }

        }
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun initView() {
        radioAdapter = context?.let {
            RadioStreaminAdapter(it, doneClick = {
                val song = mSongList[it]
                (context as MainActivity).play(mASongList, song)
                homeViewModel.exeApiProgram(song.id)
            })
        }!!
        rcvRadioStreaming.layoutManager =LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvRadioStreaming.apply { adapter = radioAdapter }
        val song = Song(0, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3", "artist",
                "albumArt", "30000", 3, false)
        val song1 = Song(1, "VOV2", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partner" +
                "tok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y" +
                "0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM", "artist", "albumArt", "30000", 3, false)
        val song2 = Song(2, "VOV3", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
                "artist", "albumArt", "30000", 3, false)
        val song3 = Song(3, "VOV4", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM",
                "artist", "albumArt", "30000", 3, false)

//        mSongList.add(song)
//        mSongList.add(song1)
//        mSongList.add(song2)
//        mSongList.add(song3)
//
//        mASongList!!.add(song)
//        mASongList!!.add(song1)
//        mASongList!!.add(song2)
//        mASongList!!.add(song3)
//        radioAdapter.setDatas(mSongList)
        initEvent()
        initEeg()
        initAutoViewpage()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initEvent() {
        icPlay.setOnSafeClickListener {
            (context as MainActivity).toggle()
        }
        icMute.setOnSafeClickListener {
            val audioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (isMute) {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                isMute = false;
                icMute.setImageDrawable(resources.getDrawable(R.drawable.ic_unmute))
            } else {
                icMute.setImageDrawable(resources.getDrawable(R.drawable.ic_mute))
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                isMute = true;
            }
        }
    }

    private fun initEeg() {
        epgAdapter = context?.let {
            EPGAdapter(it, doneClick = {

            })
        }!!
        rcvEPG.layoutManager = GridLayoutManager(activity, 1)
        rcvEPG.apply { adapter = epgAdapter }
//        for( i in 0..5){
//            val obj = Epg(i, "VOV1", getString(R.string.tv_sort_news_demo), "6h:30")
//            mEpgList.add(obj)
//        }
        epgAdapter.setDatas(mEpgList)
    }

    private fun initAutoViewpage() {
        val images = ArrayList<Int>()
        images.add(R.drawable.placeholder)
        images.add(R.drawable.img_cover_news)
        images.add(R.drawable.img_auto_scroll)
        vpAutoScroll.adapter = context?.let { ImagesAdapter(it, images) }
        vpAutoScroll.setInterval(3000)
        vpAutoScroll.setDirection(AutoScrollViewPager.Direction.RIGHT)
        vpAutoScroll.setCycle(true)
        vpAutoScroll.setBorderAnimation(true)
        vpAutoScroll.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
        vpAutoScroll.startAutoScroll()
        tlAutoVp.setupWithViewPager(vpAutoScroll)
    }

    override fun initViewModel() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.loading.observeForever(this::showProgressDialog)
        homeViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            // initViewPager();
        })
        //homeViewModel.insert(mSongList)
        homeViewModel.loadAllUser(this)
        homeViewModel.exeApiGetChannel()
    }

    override fun observable() {
        homeViewModel.loadSong.observe(this, Observer {
            Timber.e("list"+it.size)
            mSongList.clear()
            mASongList!!.clear()
            mASongList!!.addAll(it)
            mSongList.addAll(it)
            radioAdapter.setDatas(it)
        })
        homeViewModel.mListProgram.observe(this, Observer {
            mEpgList.clear()
            mEpgList.addAll(it)
            epgAdapter.setDatas(mEpgList)
        })
        with(songPlayerViewModel) {

//            songDurationData.observe(this@HomeFragment, Observer {
//                song_player_progress_seek_bar.max = it
//            })
//
//            songPositionTextData.observe(this@HomeFragment,
//                Observer { t -> song_player_passed_time_text_view.text = t })
//
//            songPositionData.observe(this@HomeFragment, Observer {
//                song_player_progress_seek_bar.progress = it
//            })
//
//            isRepeatData.observe(this@HomeFragment, Observer {
//                song_player_repeat_image_view.setImageResource(
//                    if (it) R.drawable.ic_repeat_one_color_primary_vector
//                    else R.drawable.ic_repeat_one_black_vector
//                )
//            })
//
//            isShuffleData.observe(this@HomeFragment, Observer {
//                song_player_shuffle_image_view.setImageResource(
//                    if (it) R.drawable.ic_shuffle_color_primary_vector
//                    else R.drawable.ic_shuffle_black_vector
//                )
//            })

            isPlayData.observe(this@HomeFragment, Observer {
                icPlay.setImageResource(if (it) R.drawable.ic_play else R.drawable.ic_pause)
            })

            playerData.observe(this@HomeFragment, Observer {
                //loadInitialData(it)
            })
        }
    }


}