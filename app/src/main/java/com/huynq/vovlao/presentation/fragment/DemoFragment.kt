package com.huynq.vovlao.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*


@Suppress("DEPRECATION")
class DemoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(): DemoFragment {
            val fragment = DemoFragment()
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

    }

    override fun observable() {

    }


}