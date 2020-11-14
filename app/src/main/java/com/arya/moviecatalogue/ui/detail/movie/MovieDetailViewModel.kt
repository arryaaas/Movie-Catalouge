package com.arya.moviecatalogue.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.vo.Resource

class MovieDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int) {
        this.movieId.value = movieId
    }

    var movie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { mMovieId ->
        movieRepository.getMovieDetail(mMovieId)
    }

    fun setFavorite() {
        val movieResource = movie.value
        if (movieResource != null) {
            val movieEntity = movie.value?.data as MovieEntity
            val newState = !movieEntity.favorite
            movieRepository.setMovieFavorite(movieEntity, newState)
        }
    }
}