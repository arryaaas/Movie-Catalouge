package com.arya.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvEntity

class FavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> = movieRepository.getFavoriteMovie()

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = movieRepository.getFavoriteTv()
}