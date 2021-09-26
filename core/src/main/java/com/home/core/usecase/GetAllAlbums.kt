package com.home.core.usecase

import com.home.core.data.Album
import com.home.core.repository.AlbumRepository

class GetAllAlbums(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(): List<Album>? = albumRepository.getAllAlbums()
}