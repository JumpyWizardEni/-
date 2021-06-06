package com.jumpywiz.tinkofftest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.Source
import com.jumpywiz.tinkofftest.presentation.ui.State
import com.jumpywiz.tinkofftest.presentation.viewmodels.MainViewModel
import com.jumpywiz.tinkofftest.repository.MainRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class ViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    var dataObserver: Observer<Gif?>? = null

    @Mock
    var repos: MainRepository? = null

    private var viewModel: MainViewModel? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repos!!)
    }

    @Test
    fun testObserve() {
        viewModel!!.getData().observeForever(dataObserver!!)
        viewModel!!.setData(Gif("url", Source.DB, "label", 0))
        verify(dataObserver, timeout(5000).times(1))!!
            .onChanged(Gif("url", Source.DB, "label", 0))
    }


}