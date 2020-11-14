package com.arya.moviecatalogue.ui.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arya.moviecatalogue.BuildConfig.URL_POSTER
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.items.view.*

class MovieAdapter internal constructor(
        private val onMovieClick: (movieEntity: MovieEntity) -> Unit
): PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieEntity: MovieEntity) {
            with(itemView) {
                tv_title.text = movieEntity.title
                tv_release.text = movieEntity.releaseDate
                tv_overview.text = movieEntity.overview
                Glide.with(context)
                    .load(URL_POSTER + movieEntity.posterPath)
                    .transform(CenterCrop())
                    .into(img_poster)
                itemView.setOnClickListener {
                    onMovieClick.invoke(movieEntity)
                }
            }
        }
    }
}