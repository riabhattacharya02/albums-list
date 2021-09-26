package com.home.albums.framework.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.home.albums.framework.UseCases
import com.home.albums.framework.di.appModule
import com.home.albums.util.TestContextProvider
import com.home.core.repository.AlbumRepository
import com.home.core.usecase.GetAllAlbums
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class AlbumListViewModelTest : KoinTest {

    @MockK
    private var mockApplication: Application = mockk(relaxed = true)

    @MockK
    private var mockAlbumRepository: AlbumRepository = mockk()

    @MockK
    private var mockUseCases: UseCases = UseCases(GetAllAlbums(mockAlbumRepository))

    private val testContextProvider = TestContextProvider()
    private lateinit var albumListViewModel: AlbumListViewModel

    @Rule
    @JvmField
    val instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            modules(appModule)
        }
        albumListViewModel = AlbumListViewModel(mockApplication, mockUseCases, testContextProvider)

        Dispatchers.setMain(testContextProvider.testCoroutineDispatcher)
    }

    @Test
    fun `test list of albums when server returns empty data`() {
        coEvery { mockUseCases.getAllAlbums() } coAnswers { emptyList() }

        albumListViewModel.albums.observeForever {}
        albumListViewModel.getAlbums()
        testContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        assert(albumListViewModel.albums.value?.size == 0)
    }

    @Test
    fun `test list of albums when server returns valid data`() {
        coEvery { mockUseCases.getAllAlbums() } coAnswers { listOf(mockk(), mockk()) }

        albumListViewModel.albums.observeForever {}
        albumListViewModel.getAlbums()
        testContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        assert(albumListViewModel.albums.value?.size == 2)
    }

    @Test
    fun `test list of albums when server returns error response`() {
        coEvery { mockUseCases.getAllAlbums() } coAnswers { null }

        albumListViewModel.albums.observeForever {}
        albumListViewModel.getAlbums()
        testContextProvider.testCoroutineDispatcher.advanceUntilIdle()

        assert(albumListViewModel.albums.value?.size == 0)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testContextProvider.testCoroutineDispatcher.cleanupTestCoroutines()
        stopKoin()
    }
}