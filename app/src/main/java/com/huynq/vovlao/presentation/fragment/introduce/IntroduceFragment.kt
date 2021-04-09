package com.huynq.vovlao.presentation.fragment.introduce

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Language
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.LanguageAdapter
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.fragment.news.DetailNewsFragment
import com.huynq.vovlao.presentation.viewmodel.IntroduceViewModel
import com.huynq.vovlao.utils.LanguageUtils
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.fragment_language.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import kotlinx.android.synthetic.main.fragment_recycleview.rcvAll


@Suppress("DEPRECATION")
class IntroduceFragment : BaseFragment() {

    lateinit var mainViewModel: IntroduceViewModel
    val mListLan: MutableList<Language> = ArrayList()
    lateinit var adapterNews: LanguageAdapter
    companion object {
        fun newInstance(): IntroduceFragment {
            val fragment = IntroduceFragment()
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
        return R.layout.fragment_language
    }

    override fun initView() {
        adapterNews = context?.let {
            LanguageAdapter(it, doneClick = {
                for (lan in mListLan){
                    if (lan.id == it.id){
                        lan.isChecked = true
                    }else
                        lan.isChecked = false
                }
                adapterNews.setDatas(mListLan)
            })
        }!!
        rcvLanguage.apply { adapter = adapterNews }
        adapterNews.setDatas(LanguageUtils().getLanguageData() as List<Language>)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(IntroduceViewModel::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getUUID()?.let { mainViewModel.exeApi(this, it) }
        }
    }

    override fun observable() {

    }
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    fun getUUID(): String? {
        var deviceId = ""
        deviceId = Settings.Secure.getString(getContext()?.getContentResolver(), Settings.Secure.ANDROID_ID)
        return deviceId.toString()
    }

}