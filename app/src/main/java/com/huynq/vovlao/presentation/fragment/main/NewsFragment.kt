package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.NewCategory
import com.huynq.vovlao.presentation.adapter.MainViewPagerAdapter
import com.huynq.vovlao.presentation.fragment.DemoFragment
import com.huynq.vovlao.presentation.fragment.news.ListNewsFragment
import com.huynq.vovlao.presentation.viewmodel.HomeViewModel
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_news.*


@Suppress("DEPRECATION")
class NewsFragment : BaseFragment() {

    lateinit var newViewModel: NewsViewModel
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

    }

    override fun initViewModel() {
        newViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newViewModel.loading.observeForever(this::showProgressDialog)
        newViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            // initViewPager();
        })
        newViewModel.exeGetNewCategory()
    }

    override fun observable() {
        newViewModel.loadNewsCategory.observe(this, Observer {
            initViewPager(it)
        })
    }
    private fun initViewPager(list: List<NewCategory>) {
        val adapter = MainViewPagerAdapter(childFragmentManager)
        for (category in list){
            adapter.addFragment(ListNewsFragment.newInstance(category), category.name)
        }
        vpNews.adapter = adapter
        vpNews.setOffscreenPageLimit(list.size)
        vpNews.setPageScrollEnabled(true)
        tlNews.setupWithViewPager(vpNews)
    }

}