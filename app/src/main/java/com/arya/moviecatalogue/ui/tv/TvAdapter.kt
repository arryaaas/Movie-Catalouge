package com.arya.moviecatalogue.ui.tv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arya.moviecatalogue.BuildConfig.URL_POSTER
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.items.view.*

class TvAdapter internal constructor(
        private val onMovieClick: (tvEntity: TvEntity) -> Unit
): PagedListAdapter<TvEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return TvViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }

    inner class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvEntity: TvEntity) {
            with(itemView) {
                tv_title.text = tvEntity.name
                tv_release.text = tvEntity.firstAirDate
                tv_overview.text = tvEntity.overview
                Glide.with(context)
                    .load(URL_POSTER + tvEntity.posterPath)
                    .transform(CenterCrop())
                    .into(img_poster)
                itemView.setOnClickListener {
                    onMovieClick.invoke(tvEntity)
                }
            }
        }
    }
}