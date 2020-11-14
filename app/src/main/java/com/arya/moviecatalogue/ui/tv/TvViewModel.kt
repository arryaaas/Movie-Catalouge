package com.arya.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.vo.Resource

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTv(): LiveData<Resource<PagedList<TvEntity>>> = movieRepository.getAllTv()

}