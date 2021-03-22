package com.huynq.vovlao.presentation.fragment.news

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_recycleview.*


@Suppress("DEPRECATION")
class ListNewsFragment : BaseFragment() {
    lateinit var mainViewModel: NewsViewModel
    val mListNews: MutableList<News> = ArrayList()
    lateinit var adapterNews: NewsAdapter

    companion object {
        fun newInstance(): ListNewsFragment {
            val fragment = ListNewsFragment()
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
            NewsAdapter(it, doneClick = {
                (context as MainActivity).openFragment(DetailNewsFragment.newInstance(mListNews[it]), true)
            })
        }!!
        rcvAll.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvAll.apply { adapter = adapterNews }
        mListNews.add(News(0 , "Kỳ họp hội thứ 10 Quốc hội khóa VII của Lào thông qua nhân sự chính phủ", "",getString(R.string.tv_sort_news_demo)
                , getString(R.string.tv_des_news_demo), "20/3/2021"))
        mListNews.add(News(0 , "Kỳ họp hội thứ 10 Quốc hội khóa VII của Lào thông qua nhân sự chính phủ", "",getString(R.string.tv_sort_news_demo)
                , getString(R.string.tv_des_news_demo), "20/3/2021"))
        mListNews.add(News(0 , "Kỳ họp hội thứ 10 Quốc hội khóa VII của Lào thông qua nhân sự chính phủ", "",getString(R.string.tv_sort_news_demo)
                , getString(R.string.tv_des_news_demo), "20/3/2021"))
        mListNews.add(News(0 , "Kỳ họp hội thứ 10 Quốc hội khóa VII của Lào thông qua nhân sự chính phủ", "",getString(R.string.tv_sort_news_demo)
                , getString(R.string.tv_des_news_demo), "20/3/2021"))

        adapterNews.setDatas(mListNews)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun observable() {

    }


}