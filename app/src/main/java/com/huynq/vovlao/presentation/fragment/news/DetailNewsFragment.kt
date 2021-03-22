package com.huynq.vovlao.presentation.fragment.news

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_news_detail.*


@Suppress("DEPRECATION")
class DetailNewsFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance(news: News): DetailNewsFragment {
            val fragment = DetailNewsFragment()
            val args = Bundle()
            args.putSerializable("news", news)
            fragment.setArguments(args)
            return fragment
        }
    }

    lateinit var news: News
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getSerializable("news")?.let {
            news = it as News
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news_detail
    }

    override fun initView() {
        tvDateTimeNewsDetail.text = news.dateTime
        tvDescriptionNews.text = news.description
        tvTitleDetail.text = news.title
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}