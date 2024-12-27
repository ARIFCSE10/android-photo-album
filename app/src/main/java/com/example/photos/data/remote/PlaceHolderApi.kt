package com.example.photos.data.remote

import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query


interface PlaceHolderApi {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("photos")
    suspend fun getPhotos(
        @Query("albumId")
        albumId: Int
    ): List<PhotoDto>
}