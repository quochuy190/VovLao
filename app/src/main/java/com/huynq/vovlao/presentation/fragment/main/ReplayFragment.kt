package com.huynq.vovlao.presentation.fragment.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.android.player.SongPlayerViewModel
import com.android.player.model.ASong
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Program
import com.huynq.vovlao.data.model.ProgramType
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.presentation.activity.MainActivity
import com.huynq.vovlao.presentation.adapter.ProgramReplayRadioAdapter
import com.huynq.vovlao.presentation.adapter.ProgramTypeAdapter
import com.huynq.vovlao.presentation.viewmodel.ReplayViewModel
import com.huynq.vovlao.widget.StartSnapHelper
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_replay.*


@Suppress("DEPRECATION")
class ReplayFragment : BaseFragment() {
    val mListChannel: MutableList<ProgramType> = ArrayList()
    val mListSong: MutableList<Song> = ArrayList()
    private var mASongList: MutableList<ASong>? = mutableListOf()
    lateinit var adapterChannel: ProgramTypeAdapter
    val mListProgram: MutableList<Program> = ArrayList()
    lateinit var adapterProgram: ProgramReplayRadioAdapter
    lateinit var replayViewModel: ReplayViewModel
    val songPlayerViewModel: SongPlayerViewModel = SongPlayerViewModel.getPlayerViewModelInstance()
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
            ProgramTypeAdapter(it, doneClick = {
                replayViewModel.exeProgramByTypeId(it.id)
            })
        }!!
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvListChanelRadio.layoutManager = layoutManager
        val startSnapHelper: SnapHelper = StartSnapHelper()
        startSnapHelper.attachToRecyclerView(rcvListChanelRadio)
        rcvListChanelRadio.apply { adapter = adapterChannel }
//        for (i in 0..10){
//            mListChannel.add(Song(i, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
//                "artist", "albumArt", "duration", 3, false))
//        }
//        adapterChannel.setDatas(mListChannel)
        adapterProgram = activity?.let {
            ProgramReplayRadioAdapter(it, doneClick = {
                val song = mListSong[it]
                (context as MainActivity).play(mASongList, song)
                (context as MainActivity).isShowCardPlay(true)
            })
        }!!
        var layoutManagerProgram =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListProgramReplay.layoutManager = layoutManagerProgram
        val startSnapHelperProgram: SnapHelper = StartSnapHelper()
        startSnapHelperProgram.attachToRecyclerView(rcvListProgramReplay)
        rcvListProgramReplay.apply { adapter = adapterProgram }
//        for (i in 0..10){
//            mListProgram.add(Song(i, "VOV1", "https://rfivietnamien96k.ice.infomaniak.ch/rfivietnamien-96k.mp3",
//                "artist", "albumArt", "duration", 3, false))
//        }
//        adapterProgram.setDatas(mListProgram)
    }

    override fun initViewModel() {
        replayViewModel = ViewModelProvider(this).get(ReplayViewModel::class.java)
        replayViewModel.loading.observeForever(this::showProgressDialog)
        replayViewModel.error.observeForever({ throwable ->
            showDialogMessage(context, getString(R.string.system_error))
            // initViewPager();
        })
        replayViewModel.exeProgramType()
    }

    override fun observable() {
        replayViewModel.loadProgramType.observe(this, Observer {
            if (it.size > 0) {
                mListChannel.clear()
                mListChannel.addAll(it)
                mListChannel[0].isSelected = true
                adapterChannel.setDatas(mListChannel)
                replayViewModel.exeProgramByTypeId(mListChannel[0].id)
            }
        })
        replayViewModel.loadProgram.observe(this, Observer {
            mListProgram.clear()
            mASongList!!.clear()
            mListSong.clear()
            mListProgram.addAll(it)
            for (program in mListProgram) {
                mListSong.add(
                    Song(
                        program.id,
                        program.title,
                        program.programLink,
                        program.typeName,
                        program.programImage,
                        "" + program.duration,
                        2,
                        false
                    )
                )
                mASongList!!.add(
                    Song(
                        program.id,
                        program.title,
                        program.programLink,
                        program.typeName,
                        program.programImage,
                        "" + program.duration,
                        2,
                        false
                    )
                )
            }
            adapterProgram.setDatas(mListProgram)
        })

        with(songPlayerViewModel) {

//            songDurationData.observe(this@HomeFragment, Observer {
//                song_player_progress_seek_bar.max = it
//            })
//
//            songPositionTextData.observe(this@HomeFragment,
//                Observer { t -> song_player_passed_time_text_view.text = t })
//
//            songPositionData.observe(this@HomeFragment, Observer {
//                song_player_progress_seek_bar.progress = it
//            })
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

            isPlayData.observe(this@ReplayFragment, Observer {
               // icPlay.setImageResource(if (it) com.huynq.vovlao.R.drawable.ic_play else com.huynq.vovlao.R.drawable.ic_pause)
                if (!it){
                    for (program in mListProgram) {
                        program.isSelected = false
                    }
                    adapterProgram.setDatas(mListProgram)
                }
            })

            playerData.observe(this@ReplayFragment, Observer {
                loadInitialData(it)
            })
        }
    }

    private fun loadInitialData(it: ASong?) {
        if (mListProgram.size==0)
            return
        for (program in mListProgram) {
            if (it != null) {
                if (program.id == it.songId){
                    program.isSelected = true
                }else
                    program.isSelected = false
            }else
                program.isSelected = false
        }
        adapterProgram.setDatas(mListProgram)
    }


}