package com.huynq.vovlao.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.android.player.BaseSongPlayerActivity
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.fragment.introduce.IntroduceFragment
import com.huynq.vovlao.presentation.fragment.main.MainFragment
import com.vbeeon.iotdbs.utils.openFragment
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class IntroduceActivity : BaseSongPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
        openFragment(IntroduceFragment(), false)
    }
    private fun initEvent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
        }
        Timber.e("")
//        with(songPlayerViewModel) {
//
//            songDurationData.observe(this@MainActivity, Observer {
//               // song_player_progress_seek_bar.max = it
//            })
//
//            songPositionTextData.observe(this@MainActivity,
//                Observer {
//                    t -> //song_player_passed_time_text_view.text = t
//                })
//
//            songPositionData.observe(this@MainActivity, Observer {
//                //song_player_progress_seek_bar.progress = it
//            })
//
//            isRepeatData.observe(this@MainActivity, Observer {
//                song_player_repeat_image_view.setImageResource(
//                    if (it) R.drawable.ic_repeat_one_color_primary_vector
//                    else R.drawable.ic_repeat_one_black_vector
//                )
//            })
//
//            isShuffleData.observe(this@MainActivity, Observer {
//                song_player_shuffle_image_view.setImageResource(
//                    if (it) R.drawable.ic_shuffle_color_primary_vector
//                    else R.drawable.ic_shuffle_black_vector
//                )
//            })
//
//            isPlayData.observe(this@MainActivity, Observer {
//               // song_player_toggle_image_view.setImageResource(if (it) R.drawable.ic_pause else R.drawable.exo_icon_play)
//            })
//
//            playerData.observe(this@MainActivity, Observer {
//                loadInitialData(it)
//            })
//        }
    }
}