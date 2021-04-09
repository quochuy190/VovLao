package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Language
import com.huynq.vovlao.databinding.ItemLanguageBinding
import com.vbeeon.iotdbs.utils.gone
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.visible
import timber.log.Timber


class LanguageAdapter internal constructor(var context: Context, val doneClick: (Language) -> Unit) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    private var listScript = emptyList<Language>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemLanguageBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemLanguageBinding

        fun bind(roomEntity: Language?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            if (roomEntity!!.isChecked){
                itemRoomBinding.cbLanguage.visible()
                itemRoomBinding.cbLanguage.setImageDrawable(context.getDrawable(R.drawable.ic_check_active))
            }else{
                itemRoomBinding.cbLanguage.gone()
            }
            itemRoomBinding.imgLanguage.setImageDrawable(context.getDrawable(roomEntity.imgSource))
        }

        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLanguageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_language, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listScript.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listScript[position])
        holder.itemView.setOnSafeClickListener {
            Timber.d("click item")
            doneClick(listScript[position])
        }

    }

    internal fun setDatas(list: List<Language>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}