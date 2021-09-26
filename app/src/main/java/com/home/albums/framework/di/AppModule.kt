package com.home.albums.framework.di

import com.home.albums.framework.AlbumServiceDataSource
import com.home.albums.framework.UseCases
import com.home.albums.framework.viewmodel.AlbumListViewModel
import com.home.albums.util.CoroutineContextProvider
import com.home.core.repository.AlbumDataSource
import com.home.core.repository.AlbumRepository
import com.home.core.usecase.GetAllAlbums
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UseCases(get()) }
    single { AlbumRepository(get()) }
    single { CoroutineContextProvider() }
    single<AlbumDataSource> { AlbumServiceDataSource() }

    factory { GetAllAlbums(get()) }
    viewModel { AlbumListViewModel(get(), get(), get()) }
}