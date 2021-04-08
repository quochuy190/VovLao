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
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.databinding.ItemRadioStreamingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class RadioStreaminAdapter internal constructor(
    var context: Context,
    val doneClick: (Int) -> Unit
) : RecyclerView.Adapter<RadioStreaminAdapter
.ViewHolder>() {
    private var listScript = emptyList<Song>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemRadioStreamingBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemRadioStreamingBinding

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(roomEntity: Song?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            if (roomEntity!!.isSelected) {
                itemRoomBinding.llImg.background =
                    context.getDrawable(R.drawable.bgr_circle_selected)
            } else {
                itemRoomBinding.llImg.background =
                    context.getDrawable(R.drawable.bgr_circle_default)
            }
            Glide.with(context).load(roomEntity.albumArt)
                .placeholder(R.drawable.menu_radio)
                .into(itemRoomBinding.imgLogoRadio)
        }

        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRadioStreamingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_radio_streaming, parent, false
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

    internal fun setDatas(list: List<Song>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}