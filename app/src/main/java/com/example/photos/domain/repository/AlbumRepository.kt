package com.example.photos.domain.repository

import com.example.photos.data.remote.dto.AlbumDto

interface AlbumRepository {
    suspend fun getAlbums(): List<AlbumDto>
}