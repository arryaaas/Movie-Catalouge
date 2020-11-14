package com.arya.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.utils.AppExecutors
import com.arya.moviecatalogue.utils.DataDummy
import com.arya.moviecatalogue.utils.LiveDataTestUtil
import com.arya.moviecatalogue.utils.PagedListUtil
import com.arya.moviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponse = DataDummy.generateRemoteDummyMovie()
    private val movieId = movieResponse[0].id
    private val tvResponse = DataDummy.generateRemoteDummyTv()
    private val tvId = tvResponse[0].id

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getAllMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllTv() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieRepository.getAllTv()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTv()))
        verify(local).getAllTv()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovieDetail(DataDummy.generateDummyMovie()[0], false)
        `when`<LiveData<MovieEntity>>(movieId?.let { local.getMovieById(it) }).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId ?: 0))
        movieId?.let { verify(local).getMovieById(it) }
        assertNotNull(movieEntities.data)
    }

    @Test
    fun getTvDetail() {
        val dummyEntity = MutableLiveData<TvEntity>()
        dummyEntity.value = DataDummy.generateDummyTvDetail(DataDummy.generateDummyTv()[0], false)
        `when`<LiveData<TvEntity>>(tvId?.let { local.getTvById(it) }).thenReturn(dummyEntity)

        val tvEntities = LiveDataTestUtil.getValue(movieRepository.getTvDetail(tvId ?: 0))
        tvId?.let { verify(local).getTvById(it) }
        assertNotNull(tvEntities.data)
    }
}