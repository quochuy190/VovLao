package com.huynq.vovlao.presentation.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.player.model.ASong
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Epg
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.EPGAdapter
import com.huynq.vovlao.presentation.adapter.ImagesAdapter
import com.huynq.vovlao.presentation.adapter.RadioStreaminAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager


@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {
    val mSongList: MutableList<Song> = ArrayList()
    val mEpgList: MutableList<Epg> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var radioAdapter: RadioStreaminAdapter
    lateinit var epgAdapter: EPGAdapter
    private var mASongList: MutableList<ASong>? = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        radioAdapter = context?.let {
            RadioStreaminAdapter(it, doneClick = {
                (context as MainActivity).play(mASongList, mSongList[it])
            })
        }!!
        rcvRadioStreaming.layoutManager = GridLayoutManager(activity, 4)
        rcvRadioStreaming.apply { adapter = radioAdapter }
        val song = Song(0, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3"
            , "artist", "albumArt", "duration" ,3, false)
        val song1 = Song(1, "VOV2", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM"
            , "artist", "albumArt", "duration", 3, false)
        val song2 = Song(2, "VOV3", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
            "artist", "albumArt", "duration", 3, false)
        val song3 = Song(3, "VOV4", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM",
            "artist", "albumArt", "duration", 3, false)
        mSongList.add(song)
        mSongList.add(song1)
        mSongList.add(song2)
        mSongList.add(song3)
        mASongList!!.add(song)
        mASongList!!.add(song1)
        mASongList!!.add(song2)
        mASongList!!.add(song3)
        radioAdapter.setDatas(mSongList)

        initEeg()
        initAutoViewpage()
    }

    private fun initEeg() {
        epgAdapter = context?.let {
            EPGAdapter(it, doneClick = {

            })
        }!!
        rcvEPG.layoutManager = GridLayoutManager(activity, 1)
        rcvEPG.apply { adapter = epgAdapter }
        for( i in 0..5){
            val obj = Epg(0, "VOV1")
            mEpgList.add(obj)
        }
        epgAdapter.setDatas(mEpgList)
    }

    private fun initAutoViewpage() {
        val images = ArrayList<Int>()
        images.add(R.drawable.placeholder)
        images.add(R.drawable.menu_newspaper)
        images.add(R.drawable.menu_radio)
        vpAutoScroll.adapter = context?.let { ImagesAdapter(it, images) }
        vpAutoScroll.setInterval(2000)
        vpAutoScroll.setDirection(AutoScrollViewPager.Direction.RIGHT)
        vpAutoScroll.setCycle(true)
        vpAutoScroll.setBorderAnimation(true)
        vpAutoScroll.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
        vpAutoScroll.startAutoScroll()
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}