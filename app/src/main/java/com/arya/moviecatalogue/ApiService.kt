package com.arya.moviecatalogue

import com.arya.moviecatalogue.data.source.remote.response.MovieResponse
import com.arya.moviecatalogue.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/discover/movie")
    fun discoverMovie(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Call<MovieResponse>

    @GET("/3/discover/tv")
    fun discoverTv(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Call<TvResponse>
}