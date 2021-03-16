package com.huynq.vovlao.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_demo
    }

    override fun initView() {
        context?.let { Glide.with(it).load("https://wallpaperaccess.com/full/51607.jpg").into(imgDemo) }

    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}