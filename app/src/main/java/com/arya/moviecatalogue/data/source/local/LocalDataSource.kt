package com.arya.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.data.source.local.room.CatalogDao

class LocalDataSource private constructor(private val mCatalogDao: CatalogDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogDao: CatalogDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogDao)
    }

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mCatalogDao.getMovie()

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = mCatalogDao.getFavoriteMovie()

    fun getMovieById(id: Int): LiveData<MovieEntity> = mCatalogDao.getMovieById(id)

    fun insertMovie(movies: List<MovieEntity>) = mCatalogDao.insertMovie(movies)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mCatalogDao.updateMovie(movie)
    }

    fun getAllTv(): DataSource.Factory<Int, TvEntity> = mCatalogDao.getTv()

    fun getFavoriteTv(): DataSource.Factory<Int, TvEntity> = mCatalogDao.getFavoriteTv()

    fun getTvById(id: Int): LiveData<TvEntity> = mCatalogDao.getTvById(id)

    fun insertTv(tvshows: List<TvEntity>) = mCatalogDao.insertTv(tvshows)

    fun setTvFavorite(tvshow: TvEntity, newState: Boolean) {
        tvshow.favorite = newState
        mCatalogDao.updateTv(tvshow)
    }
}