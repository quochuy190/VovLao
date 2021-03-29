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
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.viewmodel.IntroduceViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment


@Suppress("DEPRECATION")
class IntroduceFragment : BaseFragment() {

    lateinit var mainViewModel: IntroduceViewModel

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
        return R.layout.fragment_demo
    }

    override fun initView() {

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