package com.arya.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.MovieDataSource
import com.arya.moviecatalogue.data.NetworkBoundResource
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.data.source.remote.ApiResponse
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.data.source.remote.response.MovieResult
import com.arya.moviecatalogue.data.source.remote.response.TvResult
import com.arya.moviecatalogue.utils.AppExecutors
import com.arya.moviecatalogue.vo.Resource

class FakeMovieRepository constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors)
    : MovieDataSource {

    override fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResult>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResult>>> =
                    remoteDataSource.getAllMovie()

            override fun saveCallResult(data: List<MovieResult>) {
                val movieList = data.map {
                    MovieEntity(it.id, it.title, it.releaseDate, it.overview, it.posterPath, false)
                }
                localDataSource.insertMovie(movieList)
            }

        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResult>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                    data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<TvResult>>> =
                    remoteDataSource.getAllTv()

            override fun saveCallResult(data: List<TvResult>) {
                val tvList = data.map {
                    TvEntity(it.id, it.name, it.firstAirDate, it.overview, it.posterPath, false)
                }
                localDataSource.insertTv(tvList)
            }

        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<MovieResult>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                    localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                    data == null

            override fun createCall(): LiveData<ApiResponse<List<MovieResult>>> =
                    remoteDataSource.getAllMovie()

            override fun saveCallResult(data: List<MovieResult>) {

            }

        }.asLiveData()
    }

    override fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, List<TvResult>>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                    localDataSource.getTvById(tvId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                    data == null

            override fun createCall(): LiveData<ApiResponse<List<TvResult>>> =
                    remoteDataSource.getAllTv()

            override fun saveCallResult(data: List<TvResult>) {

            }

        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
            appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }

    override fun setTvFavorite(tv: TvEntity, state: Boolean) =
            appExecutors.diskIO().execute { localDataSource.setTvFavorite(tv, state) }

}