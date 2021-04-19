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
import com.huynq.vovlao.presentation.activity.IntroduceActivity
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.adapter.SettingAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.launchActivityForResult
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


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
                clickItem(it)
            })
        }!!
        rcvAll.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvAll.apply { adapter = adapterNews }
        mListNews.add(Setting(0, getString(R.string.st_introduct), R.drawable.st_introduct))
        mListNews.add(Setting(1, getString(R.string.st_language), R.drawable.st_language))
        mListNews.add(Setting(2, getString(R.string.st_rule), R.drawable.st_rule))
        mListNews.add(Setting(3, getString(R.string.st_notify), R.drawable.rl_notify))
        adapterNews.setDatas(mListNews)
    }

    private fun clickItem(position: Int) {
        when (position) {
            0 -> {
            }
            1 -> {
                (activity as MainActivity).launchActivity<IntroduceActivity> {
                    putExtra(ConstantCommon.KEY_CHANGE_LANGUAGE, true)
                }
            }
            2 -> {
            }
            3 -> {
            }
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun observable() {

    }


}