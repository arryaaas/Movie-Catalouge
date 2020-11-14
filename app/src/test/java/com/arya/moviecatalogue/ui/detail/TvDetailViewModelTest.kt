package com.arya.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.ui.detail.tv.TvDetailViewModel
import com.arya.moviecatalogue.utils.DataDummy
import com.arya.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvDetailViewModelTest {
    private lateinit var viewModel: TvDetailViewModel
    private val dummyTv = DataDummy.generateDummyTv()[0]
    private val tvId = dummyTv.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvEntity>>

    @Before
    fun setup() {
        viewModel = TvDetailViewModel(movieRepository)
        if (tvId != null) {
            viewModel.setSelectedTv(tvId.toInt())
        }
    }

    @Test
    fun getTvDetail() {
        val dummyTvDetail = Resource.success(DataDummy.generateDummyTvDetail(dummyTv, true))
        val tv = MutableLiveData<Resource<TvEntity>>()
        tv.value = dummyTvDetail

        `when`(tvId?.let { movieRepository.getTvDetail(it) }).thenReturn(tv)
        viewModel.tv.observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTvDetail)
    }
}