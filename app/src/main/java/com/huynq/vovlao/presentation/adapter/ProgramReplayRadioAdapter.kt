package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Program
import com.huynq.vovlao.databinding.ItemProgramReplayBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class ProgramReplayRadioAdapter internal constructor(
    var context: Context,
    val doneClick: (Int) -> Unit
) : RecyclerView.Adapter<ProgramReplayRadioAdapter
.ViewHolder>() {
    private var listScript = emptyList<Program>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemProgramReplayBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemProgramReplayBinding

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(roomEntity: Program?) {
            itemRoomBinding.data = roomEntity
            Glide.with(context).load(roomEntity?.programImage).placeholder(R.color.gray).into(itemRoomBinding.imgProgram)
            itemRoomBinding.executePendingBindings()
        }

        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProgramReplayBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_program_replay, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            for (i in 0..(listScript.size - 1)) {
                if (i == position) {
                    listScript[i].isSelected = true
                } else {
                    listScript[i].isSelected = false
                }
            }
            doneClick(position)
            notifyDataSetChanged()
        }

    }

    internal fun setDatas(list: List<Program>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}