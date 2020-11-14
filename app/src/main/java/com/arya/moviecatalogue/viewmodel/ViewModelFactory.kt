package com.arya.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.di.Injection
import com.arya.moviecatalogue.ui.detail.movie.MovieDetailViewModel
import com.arya.moviecatalogue.ui.detail.tv.TvDetailViewModel
import com.arya.moviecatalogue.ui.favorite.FavoriteViewModel
import com.arya.moviecatalogue.ui.movie.MovieViewModel
import com.arya.moviecatalogue.ui.tv.TvViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TvDetailViewModel::class.java) -> {
                TvDetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}