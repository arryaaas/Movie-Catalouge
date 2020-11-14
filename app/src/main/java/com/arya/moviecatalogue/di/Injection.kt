package com.arya.moviecatalogue.di

import android.content.Context
import com.arya.moviecatalogue.RetrofitService
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.room.CatalogDatabase
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {

        val database = CatalogDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(RetrofitService())
        val localDataSource = LocalDataSource.getInstance(database.catalogDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}