package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.data.model.Setting
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.adapter.SettingAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_recycleview.*


@Suppress("DEPRECATION")
class SettingFragment : BaseFragment() {
    lateinit var mainViewModel: NewsViewModel
    val mListNews: MutableList<Setting> = ArrayList()
    lateinit var adapterNews: SettingAdapter

    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
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
        return R.layout.fragment_recycleview
    }

    override fun initView() {
        adapterNews = context?.let {
            SettingAdapter(it, doneClick = {

            })
        }!!
        rcvAll.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvAll.apply { adapter = adapterNews }
        mListNews.add(Setting(0 , getString(R.string.st_introduct), R.drawable.st_introduct))
        mListNews.add(Setting(1 , getString(R.string.st_language), R.drawable.st_language))
        mListNews.add(Setting(2 , getString(R.string.st_rule), R.drawable.st_rule))
        mListNews.add(Setting(3 , getString(R.string.st_notify), R.drawable.rl_notify))
        adapterNews.setDatas(mListNews)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun observable() {

    }


}