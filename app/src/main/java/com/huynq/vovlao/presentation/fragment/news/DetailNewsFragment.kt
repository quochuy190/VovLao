package com.huynq.vovlao.presentation.fragment.news

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.presentation.viewmodel.NewsViewModel
import com.huynq.vovlao.utils.DateUtils
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class DetailNewsFragment : BaseFragment() {

    lateinit var mainViewModel: NewsViewModel

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
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = getString(R.string.title_newpage)
        Glide.with(this).load(news.urlCover).placeholder(R.color.gray).into(imgCoverNewsDetail)
        tvDateTimeNewsDetail.text = DateUtils().convertString(news.dateTime)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            // initViewPager();
        })
        mainViewModel.exeGetNewsDetail(news)
    }

    override fun observable() {
        mainViewModel.loadNewDetail.observe(this, Observer {
            tvTitleDetail.text = it.title
            wvDetail.getSettings().setJavaScriptEnabled(true);
            Timber.e(""+it.details)
            wvDetail.setBackgroundColor(Color.TRANSPARENT);
          //  wvDetail.loadDataWithBaseURL(null,"<html><body>"+it.details+"</body></html>", "text/html; charset=utf-8", "UTF-8", null);
            wvDetail.loadData("<html><body>"+it.details+"</body></html>", "text/html; charset=utf-8", "UTF-8");
        })
    }


}