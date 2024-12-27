package com.example.photos.data.repository

import com.example.photos.data.remote.PlaceHolderApi
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto
import com.example.photos.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: PlaceHolderApi
) : RemoteRepository {
    override suspend fun getAlbums(): List<AlbumDto> {
        return api.getAlbums()
    }

    override suspend fun getUsers(): List<UserDto> {
        return api.getUsers()
    }

    override suspend fun getPhotos(albumId: Int): List<PhotoDto> {
        return api.getPhotos(albumId)
    }
}