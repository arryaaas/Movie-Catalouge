package com.arya.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.vo.Resource

interface MovieDataSource {
    fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>>

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTvFavorite(tv: TvEntity, state: Boolean)
}