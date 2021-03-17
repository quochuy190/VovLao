package com.huynq.vovlao.presentation.fragment.main

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.adapter.ImagesAdapter
import com.huynq.vovlao.presentation.adapter.RadioStreaminAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager


@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {
    val mSongList: MutableList<Song> = ArrayList()
    lateinit var mainViewModel: MainViewModel
    lateinit var radioAdapter: RadioStreaminAdapter
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

            })
        }!!
        rcvRadioStreaming.layoutManager = GridLayoutManager(activity, 4)
        rcvRadioStreaming.apply { adapter = radioAdapter }
        val song = Song(0, "VOV1", "path.toString()", "artist", "albumArt", "duration", 3)
        val song1 = Song(0, "VOV2", "path.toString()", "artist", "albumArt", "duration", 3)
        val song2 = Song(0, "VOV3", "path.toString()", "artist", "albumArt", "duration", 3)
        val song3 = Song(0, "VOV4", "path.toString()", "artist", "albumArt", "duration", 3)
        mSongList.add(song)
        mSongList.add(song1)
        mSongList.add(song2)
        mSongList.add(song3)
        radioAdapter.setDatas(mSongList)
        initAutoViewpage()
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