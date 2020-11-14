package com.arya.moviecatalogue.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.vo.Resource

class TvDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var tvId = MutableLiveData<Int>()

    fun setSelectedTv(tvId: Int) {
        this.tvId.value = tvId
    }

    var tv: LiveData<Resource<TvEntity>> = Transformations.switchMap(tvId) { mTvId ->
        movieRepository.getTvDetail(mTvId)
    }

    fun setFavorite() {
        val tvResource = tv.value
        if (tvResource != null) {
            val tvEntity = tv.value?.data as TvEntity
            val newState = !tvEntity.favorite
            movieRepository.setTvFavorite(tvEntity, newState)
        }
    }
}