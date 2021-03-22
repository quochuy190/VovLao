package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.ChannelRadioAdapter
import com.huynq.vovlao.presentation.adapter.NewsAdapter
import com.huynq.vovlao.presentation.fragment.news.DetailNewsFragment
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import kotlinx.android.synthetic.main.fragment_replay.*


@Suppress("DEPRECATION")
class ReplayFragment : BaseFragment() {
    val mListChannel: MutableList<Song> = ArrayList()
    lateinit var adapterChannel: ChannelRadioAdapter
    lateinit var mainViewModel: MainViewModel
    companion object {
        fun newInstance(): ReplayFragment {
            val fragment = ReplayFragment()
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
        return R.layout.fragment_replay
    }

    override fun initView() {
        adapterChannel = context?.let {
            ChannelRadioAdapter(it, doneClick = {

            })
        }!!
        rcvListChanelRadio.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvListChanelRadio.apply { adapter = adapterChannel }
        val song = Song(0, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3"
                , "artist", "albumArt", "duration" ,3, false)
        val song1 = Song(1, "VOV2", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM"
                , "artist", "albumArt", "duration", 3, false)
        val song2 = Song(2, "VOV3", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
                "artist", "albumArt", "duration", 3, false)
        val song3 = Song(3, "VOV4", "https://23023.live.streamtheworld.com/KIROFM_SC?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTYwOTM4Nzg1MiwiaXNzIjoidGlzcnYifQ.z-_rAzo_y0cSK0oowDtVsXraYhPj3Bqcm-14sRav4eM",
                "artist", "albumArt", "duration", 3, false)
        mListChannel.add(song)
        mListChannel.add(song1)
        mListChannel.add(song2)
        mListChannel.add(song3)
//        mASongList!!.add(song)
//        mASongList!!.add(song1)
//        mASongList!!.add(song2)
//        mASongList!!.add(song3)
        adapterChannel.setDatas(mListChannel)
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}