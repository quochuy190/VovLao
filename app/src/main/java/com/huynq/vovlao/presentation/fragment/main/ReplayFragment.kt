package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.adapter.ChannelRadioAdapter
import com.huynq.vovlao.presentation.adapter.ProgramReplayRadioAdapter
import com.huynq.vovlao.presentation.viewmodel.MainViewModel
import com.huynq.vovlao.widget.StartSnapHelper
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_demo.*
import kotlinx.android.synthetic.main.fragment_recycleview.*
import kotlinx.android.synthetic.main.fragment_replay.*


@Suppress("DEPRECATION")
class ReplayFragment : BaseFragment() {
    val mListChannel: MutableList<Song> = ArrayList()
    lateinit var adapterChannel: ChannelRadioAdapter
    val mListProgram: MutableList<Song> = ArrayList()
    lateinit var adapterProgram: ProgramReplayRadioAdapter
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
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvListChanelRadio.layoutManager = layoutManager
        val startSnapHelper: SnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView(rcvListChanelRadio)
        rcvListChanelRadio.apply { adapter = adapterChannel }
        for (i in 0..10){
            mListChannel.add(Song(i, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
                "artist", "albumArt", "duration", 3, false))
        }
        adapterChannel.setDatas(mListChannel)
        adapterProgram = activity?.let {
            ProgramReplayRadioAdapter(it, doneClick = {

            })
        }!!
        var layoutManagerProgram = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListProgramReplay.layoutManager = layoutManagerProgram
        val startSnapHelperProgram: SnapHelper = StartSnapHelper()
        startSnapHelperProgram.attachToRecyclerView(rcvListProgramReplay)
        rcvListProgramReplay.apply { adapter = adapterProgram }
        for (i in 0..10){
            mListProgram.add(Song(i, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
                "artist", "albumArt", "duration", 3, false))
        }
        adapterProgram.setDatas(mListProgram)
    }

    override fun initViewModel() {

    }

    override fun observable() {

    }


}