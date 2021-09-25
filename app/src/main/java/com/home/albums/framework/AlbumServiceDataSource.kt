package com.home.albums.framework

import com.home.albums.framework.services.albumService
import com.home.core.data.Album
import com.home.core.repository.AlbumDataSource

class AlbumServiceDataSource: AlbumDataSource {
    //Storing the list in app cache which can be later moved to a Room database to
    // store locally within the app
    private var listOfAlbums: List<Album> = emptyList()

    override suspend fun getAllAlbums(): List<Album> {
        if(listOfAlbums.isEmpty()) {
            listOfAlbums = albumService.getListOfAlbums()
        }
        return listOfAlbums
    }

    //When Room is implemented, this would translate to a delete database entry operation
    fun clearListOfAlbums() {
        listOfAlbums = emptyList()
    }
}