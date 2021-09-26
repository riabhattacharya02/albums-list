package com.home.core.repository

import com.home.core.data.Album

class AlbumRepository(private val albumDataSource: AlbumDataSource) {
    suspend fun getAllAlbums(): List<Album>? = albumDataSource.getAllAlbums()
}