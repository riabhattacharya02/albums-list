package com.home.albums.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.home.albums.framework.UseCases
import com.home.albums.util.CoroutineContextProvider
import com.home.core.data.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AlbumListViewModel(
    application: Application,
    private val useCases: UseCases,
    coroutineContextProvider: CoroutineContextProvider
) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(coroutineContextProvider.IO)

    val albums = MutableLiveData<List<Album>>()

    fun getAlbums() {
        coroutineScope.launch {
            val listOfAlbums: List<Album> = useCases.getAllAlbums() ?: emptyList()
            albums.postValue(listOfAlbums)
        }
    }
}