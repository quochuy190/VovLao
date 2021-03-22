package com.huynq.vovlao.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.player.BaseSongPlayerActivity
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.fragment.main.MainFragment
import com.vbeeon.iotdbs.utils.openFragment
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class MainActivity : BaseSongPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
        openFragment(MainFragment(), false)
    }
    private fun initEvent() {
        Timber.e("")
    }
}