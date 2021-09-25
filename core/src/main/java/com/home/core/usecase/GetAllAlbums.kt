package com.home.core.usecase

import com.home.core.repository.AlbumRepository

class GetAllAlbums(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getAllAlbums()
}