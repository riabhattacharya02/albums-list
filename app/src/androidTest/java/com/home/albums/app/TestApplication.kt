package com.home.albums.app

import android.app.Application
import com.home.albums.framework.AlbumServiceDataSource
import com.home.albums.framework.UseCases
import com.home.albums.framework.viewmodel.AlbumListViewModel
import com.home.albums.util.CoroutineContextProvider
import com.home.core.repository.AlbumDataSource
import com.home.core.repository.AlbumRepository
import com.home.core.usecase.GetAllAlbums
import io.mockk.mockk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

import org.junit.Assert.*
import org.koin.android.viewmodel.dsl.viewModel

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(listOf(
                module {
                    single { mockk<UseCases>(relaxed = true) }
                    single { mockk<AlbumRepository>(relaxed = true) }
                    single { mockk<CoroutineContextProvider>(relaxed = true) }
                    single<AlbumDataSource> { mockk<AlbumServiceDataSource>(relaxed = true) }

                    factory { mockk<GetAllAlbums>(relaxed = true) }
                    viewModel { mockk<AlbumListViewModel>(relaxed = true) }
                }
            ))
        }
    }
}