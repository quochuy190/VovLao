package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Epg
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.databinding.ItemEpgHomeBinding
import com.huynq.vovlao.databinding.ItemRadioStreamingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class EPGAdapter internal constructor(var context: Context, val doneClick: (Int) -> Unit) : RecyclerView.Adapter<EPGAdapter.ViewHolder>() {
    private var listScript = emptyList<Epg>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemEpgHomeBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemEpgHomeBinding

        fun bind(roomEntity: Epg?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
//            if (roomEntity!!.isSelected){
//                val sysTitle = "<b><font color='#000000'>"+roomEntity.name+"</font></b>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
//               // itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#026BBE"));
////                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT_BOLD
//            }else{
//                val sysTitle = "<font color='#cccccc'>"+roomEntity.name+"</font>"
//                itemRoomBinding.tvTimeCalName.text = setTextHTML(sysTitle)
////                itemRoomBinding.tvTimeCalName.setTextColor(Color.parseColor("#FF000000"));
////                itemRoomBinding.tvTimeCalName.typeface = Typeface.DEFAULT
//            }
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

    internal fun setDatas(list: List<Epg>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}