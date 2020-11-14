package com.arya.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import com.arya.moviecatalogue.RetrofitService
import com.arya.moviecatalogue.data.source.remote.response.MovieResult
import com.arya.moviecatalogue.data.source.remote.response.TvResult
import com.arya.moviecatalogue.utils.EspressoIdlingResource

class RemoteDataSource(private val retrofitService: RetrofitService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: RetrofitService): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(service)
                }
    }


    fun getAllMovie(): LiveData<ApiResponse<List<MovieResult>>> {
        EspressoIdlingResource.increment()
        try {
            return retrofitService.getMovie()
        } finally {
            EspressoIdlingResource.decrement()
        }
    }

    fun getAllTv(): LiveData<ApiResponse<List<TvResult>>> {
        EspressoIdlingResource.increment()
        try {
            return retrofitService.getTv()
        } finally {
            EspressoIdlingResource.decrement()
        }
    }
}