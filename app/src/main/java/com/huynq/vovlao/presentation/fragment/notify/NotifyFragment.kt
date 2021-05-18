package com.huynq.vovlao.presentation.fragment.notify

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.data.model.Notify
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.adapter.NotifyAdapter
import com.huynq.vovlao.presentation.fragment.news.DetailNewsFragment
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_notify.*
import kotlinx.android.synthetic.main.fragment_recycleview.*


@Suppress("DEPRECATION")
class NotifyFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    val mListNews: MutableList<Notify> = ArrayList()
    lateinit var adapterNews: NotifyAdapter
    companion object {
        fun newInstance(): NotifyFragment {
            val fragment = NotifyFragment()
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
        return R.layout.fragment_notify
    }

    override fun initView() {
        adapterNews = context?.let {
            NotifyAdapter(it, doneClick = {
               // (context as MainActivity).openFragment(DetailNewsFragment.newInstance(mListNews[it]), true)
            })
        }!!
        rcvNotify.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvNotify.apply { adapter = adapterNews }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.exeNotify()
    }

    override fun observable() {
        mainViewModel.loadNotify.observe(this, Observer {
            adapterNews.setDatas(it)
        })
    }


}