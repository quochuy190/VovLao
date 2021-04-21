package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Setting
import com.huynq.vovlao.databinding.ItemEpgHomeBinding
import com.huynq.vovlao.databinding.ItemNewsBinding
import com.huynq.vovlao.databinding.ItemRadioStreamingBinding
import com.huynq.vovlao.databinding.ItemSettingBinding
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class SettingAdapter internal constructor(var context: Context, val doneClick: (Int) -> Unit)
    : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {
    private var listScript = emptyList<Setting>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemSettingBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemSettingBinding

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(roomEntity: Setting?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            itemRoomBinding.imgItemMenu.setImageDrawable(context.getDrawable(roomEntity!!.res))
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
        val binding: ItemSettingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_setting, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(position)
        }

    }

    internal fun setDatas(list: List<Setting>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}