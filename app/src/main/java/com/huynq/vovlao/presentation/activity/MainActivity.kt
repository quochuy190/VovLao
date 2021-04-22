package com.huynq.vovlao.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.android.player.BaseSongPlayerActivity
import com.android.player.exo.OnExoPlayerManagerCallback
import com.android.player.model.ASong
import com.huynq.vovlao.R
import com.huynq.vovlao.presentation.fragment.main.MainFragment
import com.huynq.vovlao.presentation.fragment.player.PlayerDetailFragment
import com.huynq.vovlao.utils.LanguageUtils
import com.vbeeon.iotdbs.data.model.MessageEventBus
import com.vbeeon.iotdbs.utils.gone
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.ArrayList

class MainActivity : BaseSongPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LanguageUtils().loadLocale()
        initEvent()
        val isChangeLanguage = intent.getBooleanExtra(ConstantCommon.KEY_CHANGE_LANGUAGE, false)
        openFragment(MainFragment.newInstance(isChangeLanguage), false)
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        stopService()
    }

    private fun initEvent() {
        cardPlay.setOnSafeClickListener {
            currentSong()?.let { it1 -> PlayerDetailFragment.newInstance(it1) }?.let { it2 ->
                openFragment(
                    it2, true)
            }
        }
        Timber.e("")
        with(songPlayerViewModel) {

            songDurationData.observe(this@MainActivity, Observer {
               // song_player_progress_seek_bar.max = it
            })

            songPositionTextData.observe(this@MainActivity, Observer {
                    t -> //song_player_passed_time_text_view.text = t
                })

            songPositionData.observe(this@MainActivity, Observer {
                //song_player_progress_seek_bar.progress = it
            })

            isRepeatData.observe(this@MainActivity, Observer {
//                song_player_repeat_image_view.setImageResource(
//                    if (it) R.drawable.ic_repeat_one_color_primary_vector
//                    else R.drawable.ic_repeat_one_black_vector
//                )
            })

            isShuffleData.observe(this@MainActivity, Observer {
//                song_player_shuffle_image_view.setImageResource(
//                    if (it) R.drawable.ic_shuffle_color_primary_vector
//                    else R.drawable.ic_shuffle_black_vector
//                )
            })

            isPlayData.observe(this@MainActivity, Observer {
               // song_player_toggle_image_view.setImageResource(if (it) R.drawable.ic_pause else R.drawable.exo_icon_play)
                if (it){
                    cardPlay.visible()
                }else
                    cardPlay.gone()

            })

            playerData.observe(this@MainActivity, Observer {
                //loadInitialData(it)
            })
        }
    }


}