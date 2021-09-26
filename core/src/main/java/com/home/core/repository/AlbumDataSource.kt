package com.home.core.repository

import com.home.core.data.Album

interface AlbumDataSource {
    suspend fun getAllAlbums(): List<Album>?
}