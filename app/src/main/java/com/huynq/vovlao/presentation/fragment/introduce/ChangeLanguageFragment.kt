package com.huynq.vovlao.presentation.fragment.introduce

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Language
import com.huynq.vovlao.presentation.activity.IntroduceActivity
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.LanguageAdapter
import com.huynq.vovlao.presentation.viewmodel.IntroduceViewModel
import com.huynq.vovlao.utils.LanguageUtils
import com.huynq.vovlao.utils.SharedPrefs
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_language.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ChangeLanguageFragment : BaseFragment() {
    var codeLan = 1
    var language: Language? = null
    lateinit var mainViewModel: IntroduceViewModel
    var mListLan: MutableList<Language> = ArrayList()
    lateinit var adapterNews: LanguageAdapter

    companion object {
        fun newInstance(): ChangeLanguageFragment {
            val fragment = ChangeLanguageFragment()
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
                language = it
                codeLan = it.id + 1
                for (lan in mListLan) {
                    if (lan.id == it.id) {
                        lan.isChecked = true
                    } else
                        lan.isChecked = false
                }
                adapterNews.setDatas(mListLan)
            })
        }!!
        rcvLanguage.apply { adapter = adapterNews }
        mListLan = LanguageUtils().getLanguageData() as MutableList<Language>
        adapterNews.setDatas(mListLan)
        btnNext.setOnSafeClickListener {
           // reload language
            SharedPrefs.instance.put(ConstantCommon.LANGUAGE, language)
           // LanguageUtils().changeLanguage(language!!)
            val intent = Intent(activity, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(ConstantCommon.KEY_CHANGE_LANGUAGE, true)
            startActivity(intent)
        }
    }


    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(IntroduceViewModel::class.java)

    }

    override fun observable() {
        mainViewModel.loading.observeForever(this::showProgressDialog)
        mainViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            // initViewPager();
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    fun getUUID(): String? {
        var deviceId = ""
        deviceId = Settings.Secure.getString(
            getContext()?.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
        return deviceId.toString()
    }

}