package com.home.albums.framework.services

import com.home.albums.util.GET_ALL_ALBUMS
import com.home.core.data.Album
import retrofit2.http.GET

interface AlbumService {
    @GET(GET_ALL_ALBUMS)
    suspend fun getListOfAlbums(): List<Album>
}