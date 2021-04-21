package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Program
import com.huynq.vovlao.databinding.ItemEpgHomeBinding
import com.huynq.vovlao.databinding.ItemRadioStreamingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class EPGAdapter internal constructor(var context: Context, val doneClick: (Int) -> Unit) : RecyclerView.Adapter<EPGAdapter.ViewHolder>() {
    private var listScript = emptyList<Program>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemEpgHomeBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemEpgHomeBinding

        fun bind(roomEntity: Program?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            Glide.with(context).load(roomEntity!!.programImage).placeholder(R.color.gray).into(itemRoomBinding.imgCoverEpg)
            itemRoomBinding.tvTime.text = roomEntity.timeStart +" - "+roomEntity.endTime
        }

        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEpgHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_epg_home, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(position)
        }

    }

    internal fun setDatas(list: List<Program>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}