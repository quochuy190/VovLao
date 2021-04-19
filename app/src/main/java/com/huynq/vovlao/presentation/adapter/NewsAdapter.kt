package com.huynq.vovlao.presentation.adapter

import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huynq.vovlao.R
import com.huynq.vovlao.data.model.Epg
import com.huynq.vovlao.data.model.News
import com.huynq.vovlao.data.model.Song
import com.huynq.vovlao.databinding.ItemEpgHomeBinding
import com.huynq.vovlao.databinding.ItemNewsBinding
import com.huynq.vovlao.databinding.ItemRadioStreamingBinding
import com.huynq.vovlao.utils.DateUtils
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import timber.log.Timber


class NewsAdapter internal constructor(var context: Context, val doneClick: (Int) -> Unit)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var listScript = emptyList<News>() // Cached copy of words

    inner class ViewHolder(itemBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemRoomBinding: ItemNewsBinding

        fun bind(roomEntity: News?) {
            itemRoomBinding.data = roomEntity
            itemRoomBinding.executePendingBindings()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                itemRoomBinding.tvSortDesNews.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            }
            itemRoomBinding.tvDateTimeNews.text = DateUtils().convertString(roomEntity!!.dateTime)
        }

        init {
            itemRoomBinding = itemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_news, parent, false)
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

    internal fun setDatas(list: List<News>) {
        this.listScript = list
        notifyDataSetChanged()
    }

}