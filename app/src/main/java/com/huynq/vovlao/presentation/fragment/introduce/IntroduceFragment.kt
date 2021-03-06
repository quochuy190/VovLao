package com.huynq.vovlao.presentation.fragment.introduce

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Language
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.presentation.activity.IntroduceActivity
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.LanguageAdapter
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.fragment.news.DetailNewsFragment
import com.huynq.vovlao.presentation.viewmodel.IntroduceViewModel
import com.huynq.vovlao.utils.LanguageUtils
import com.huynq.vovlao.utils.SharedPrefs
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.getUUID
import com.vbeeon.iotdbs.utils.launchActivity
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_language.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import kotlinx.android.synthetic.main.fragment_recycleview.rcvAll
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class IntroduceFragment : BaseFragment() {
    var codeLan = 1
    var language: Language? = null
    lateinit var mainViewModel: IntroduceViewModel
    var mListLan: MutableList<Language> = ArrayList()
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e("Fetching FCM registration token failed: "+ task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            SharedPrefs.instance.put(ConstantCommon.KEY_TOKEN_FIREBASE, token)
        })
        adapterNews = context?.let {
            LanguageAdapter(it, doneClick = {
                language = it
                codeLan = it.id +1
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
            getApiInit(codeLan)
        }
    }

    private fun getApiInit(codeLanguage: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.let { getUUID(it)?.let { mainViewModel.exeApi(it, codeLanguage) } }
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
        mainViewModel.loadInit.observe(this, Observer {
            if (it) {
                SharedPrefs.instance.put(ConstantCommon.LANGUAGE, language)
                SharedPrefs.instance.put(ConstantCommon.IS_FIRST_OPEN_APP,true)
                LanguageUtils().loadLocale()

                (activity as IntroduceActivity).launchActivity<MainActivity>()
                (activity as IntroduceActivity).finish()
            }
        })

    }



}