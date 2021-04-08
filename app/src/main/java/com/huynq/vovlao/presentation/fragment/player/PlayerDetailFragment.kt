package com.huynq.vovlao.presentation.fragment.player

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.request.CachePolicy
import com.android.player.SongPlayerViewModel
import com.android.player.model.ASong
import com.android.player.util.formatTimeInMillisToString
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_player_detail.*
import timber.log.Timber
import java.io.File


@Suppress("DEPRECATION")
class PlayerDetailFragment : BaseFragment() {
    val songPlayerViewModel: SongPlayerViewModel = SongPlayerViewModel.getPlayerViewModelInstance()
    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(): PlayerDetailFragment {
            val fragment = PlayerDetailFragment()
            val args = Bundle()
            //args.putString("jsonFile", jsonFile)
            fragment.setArguments(args)
            return fragment
        }
    }

    lateinit var songS: Song
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<Song>("jsonFile")?.let {
            songS = it
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_player_detail
    }

    override fun initView() {
        llDetailPlay.setOnSafeClickListener {

        }
        icPlayerDetail.setOnSafeClickListener {
            (context as MainActivity).toggle()
        }
    }

    override fun initViewModel() {

    }

    override fun observable() {
        with(songPlayerViewModel) {

            songDurationData.observe(this@PlayerDetailFragment, Observer {
                Timber.e(""+it)
                sekbarPlayDetail.max = it
            })
//
//            songPositionTextData.observe(this@HomeFragment,
//                Observer { t -> song_player_passed_time_text_view.text = t })
//
            songPositionData.observe(this@PlayerDetailFragment, Observer {
                sekbarPlayDetail.progress = it
                Timber.e(""+it)
            })
//
//            isRepeatData.observe(this@HomeFragment, Observer {
//                song_player_repeat_image_view.setImageResource(
//                    if (it) R.drawable.ic_repeat_one_color_primary_vector
//                    else R.drawable.ic_repeat_one_black_vector
//                )
//            })
//
//            isShuffleData.observe(this@HomeFragment, Observer {
//                song_player_shuffle_image_view.setImageResource(
//                    if (it) R.drawable.ic_shuffle_color_primary_vector
//                    else R.drawable.ic_shuffle_black_vector
//                )
//            })

            isPlayData.observe(this@PlayerDetailFragment, Observer {
                icPlayerDetail.setImageResource(if (it) R.drawable.ic_play else R.drawable.ic_pause)
            })

            playerData.observe(this@PlayerDetailFragment, Observer {
                loadInitialData(it)
            })
        }
    }

    private fun loadInitialData(aSong: ASong) {
//        song_player_title_text_view.text = aSong.title
//        song_player_singer_name_text_view.text = aSong.artist
//        song_player_total_time_text_view.text = formatTimeInMillisToString(aSong.length?.toLong()?:0L)

//        aSong.clipArt?.let {
//            song_player_image_view.load(File(it)) {
//                crossfade(true)
//                CachePolicy.ENABLED
//            }
//        }
    }
}