package com.huynq.vovlao.presentation.fragment.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.adapter.ImagesAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager


@Suppress("DEPRECATION")
class HomeFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
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