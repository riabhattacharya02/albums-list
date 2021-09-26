package com.home.core.repository

import com.home.albums.framework.di.appModule
import com.home.core.data.Album
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class AlbumRepositoryTest : KoinTest {
    private lateinit var albumRepository: AlbumRepository

    @MockK
    private var mockAlbumDataSource: AlbumDataSource = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            modules(appModule)
        }
        albumRepository = AlbumRepository(mockAlbumDataSource)
    }

    @Test
    fun `test when data source returns empty data`() {
        var listOfAlbums: List<Album>? = listOf()
        coEvery { mockAlbumDataSource.getAllAlbums() } coAnswers { emptyList() }
        runBlocking { listOfAlbums = albumRepository.getAllAlbums() }
        coVerify { albumRepository.getAllAlbums() }
        assert(listOfAlbums.isNullOrEmpty())
    }

    @Test
    fun `test when data source returns valid data`() {
        var listOfAlbums: List<Album>? = listOf(mockk(), mockk())
        coEvery { mockAlbumDataSource.getAllAlbums() } coAnswers { listOfAlbums }
        runBlocking { listOfAlbums = albumRepository.getAllAlbums() }
        coVerify { albumRepository.getAllAlbums() }
        assert(listOfAlbums!!.size == 2)
    }

    @Test
    fun `test when data source returns error response`() {
        var listOfAlbums: List<Album>? = listOf()
        coEvery { mockAlbumDataSource.getAllAlbums() } coAnswers { null }
        runBlocking { listOfAlbums = albumRepository.getAllAlbums() }
        coVerify { albumRepository.getAllAlbums() }
        assert(listOfAlbums.isNullOrEmpty())
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}