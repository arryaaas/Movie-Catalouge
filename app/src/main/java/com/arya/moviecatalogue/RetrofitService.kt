package com.arya.moviecatalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arya.moviecatalogue.data.source.remote.ApiResponse
import com.arya.moviecatalogue.data.source.remote.response.MovieResponse
import com.arya.moviecatalogue.data.source.remote.response.MovieResult
import com.arya.moviecatalogue.data.source.remote.response.TvResponse
import com.arya.moviecatalogue.data.source.remote.response.TvResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitService {

    fun getMovie(): LiveData<ApiResponse<List<MovieResult>>> {
        val movie = MutableLiveData<ApiResponse<List<MovieResult>>>()
        val dataSource = NetworkProvider.providesHttpAdapter()
                .create(ApiService::class.java)
        dataSource.discoverMovie().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movie.value = ApiResponse.success(response.body()?.result ?: emptyList())
                } else {
                    movie.value = ApiResponse.empty(emptyList())
                }

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movie.value = ApiResponse.error("${t.printStackTrace()}")
            }

        })
        return movie
    }

    fun getTv(): LiveData<ApiResponse<List<TvResult>>> {
        val tv = MutableLiveData<ApiResponse<List<TvResult>>>()
        val dataSource = NetworkProvider.providesHttpAdapter()
                .create(ApiService::class.java)
        dataSource.discoverTv().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    tv.value = ApiResponse.success(response.body()?.result ?: emptyList())
                } else {
                    tv.value = ApiResponse.empty(emptyList())
                }
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                tv.value = ApiResponse.error("${t.printStackTrace()}")
            }

        })
        return tv
    }
}