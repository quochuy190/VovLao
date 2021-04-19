package com.huynq.vovlao.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.android.player.BaseSongPlayerActivity
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.fragment.introduce.ChangeLanguageFragment
import com.huynq.vovlao.presentation.fragment.introduce.IntroduceFragment
import com.huynq.vovlao.presentation.fragment.main.MainFragment
import com.vbeeon.iotdbs.utils.gone
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class IntroduceActivity : BaseSongPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
        cardPlay.gone()
        val isChangeLanguage = intent.getBooleanExtra(ConstantCommon.KEY_CHANGE_LANGUAGE, false)
        if (isChangeLanguage)
            openFragment(ChangeLanguageFragment(), false)
        else
            openFragment(IntroduceFragment(), false)
    }

    private fun initEvent() {

    }
}