package com.huynq.vovlao.presentation.fragment.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.adapter.MainViewPagerAdapter
import com.huynq.vovlao.presentation.fragment.DemoFragment
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.gone
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class MainFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun initView() {
//        imgBack.setOnSafeClickListener {
//            Timber.e("ib_toolbar_close.setOnSafeClickListener")
//            activity?.onBackPressed()
//        }
        imgBack.gone()

        initViewPager()
    }

    private fun initViewPager() {
        tv_toolbar_title.text = getString(R.string.title_home)
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(HomeFragment(), "")
        adapter.addFragment(NewsFragment(), "")
        adapter.addFragment(ReplayFragment(), "")
        adapter.addFragment(SettingFragment(), "")
        vp_main.adapter = adapter
        vp_main.setOffscreenPageLimit(4)
        vp_main.setPageScrollEnabled(false)
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_radio -> {
                    Timber.e("home")
                    vp_main.currentItem = 0
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_home))
                    tv_toolbar_title.text = getString(R.string.title_home)
                }
                R.id.menu_newpage -> {
                    Timber.e("new")
                    vp_main.currentItem = 1
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_monitoring))
                    tv_toolbar_title.text = getString(R.string.title_newpage)
                }
                R.id.menu_replay -> {
                    Timber.e("replay")
                    vp_main.currentItem = 2
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_online))
                    tv_toolbar_title.text = getString(R.string.title_replay)
                }
                R.id.menu_setting -> {
                    Timber.e("setting")
                    vp_main.currentItem = 3
                    //( context as MainActivity).setTitleMain(getString(R.string.menu_online))
                    tv_toolbar_title.text = getString(R.string.title_setup)
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
           // initViewPager();
        })

    }

    override fun observable() {

    }


}