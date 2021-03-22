package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.adapter.MainViewPagerAdapter
import com.huynq.vovlao.presentation.fragment.DemoFragment
import com.huynq.vovlao.presentation.fragment.news.ListNewsFragment
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_news.*


@Suppress("DEPRECATION")
class NewsFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(): NewsFragment {
            val fragment = NewsFragment()
            val args = Bundle()
            //args.putString("jsonFile", jsonFile)
            fragment.setArguments(args)
            return fragment
        }
    }

    var jsonFile: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("jsonFile")?.let {
            jsonFile = it
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news
    }

    override fun initView() {
        initViewPager()

    }

    override fun initViewModel() {

    }

    override fun observable() {

    }
    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))
        adapter.addFragment(ListNewsFragment.newInstance(), getString(R.string.title_home))

        vpNews.adapter = adapter
        vpNews.setOffscreenPageLimit(10)
        vpNews.setPageScrollEnabled(true)
        tlNews.setupWithViewPager(vpNews)
    }

}