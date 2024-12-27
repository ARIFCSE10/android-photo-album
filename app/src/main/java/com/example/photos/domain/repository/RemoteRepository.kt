package com.example.photos.domain.repository

import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto

interface RemoteRepository {
    suspend fun getAlbums(): List<AlbumDto>
    suspend fun getUsers(): List<UserDto>
    suspend fun getPhotos(albumId: Int): List<PhotoDto>
}