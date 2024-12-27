package com.example.photos.data.repository

import com.example.photos.data.remote.PlaceHolderApi
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api: PlaceHolderApi
) : AlbumRepository {
    override suspend fun getAlbums(): List<AlbumDto> {
        return api.getAlbums()
    }

}