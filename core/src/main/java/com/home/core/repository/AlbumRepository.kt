package com.home.core.repository

class AlbumRepository(private val albumDataSource: AlbumDataSource) {
    suspend fun getAllAlbums() = albumDataSource.getAllAlbums()
}