package com.example.photos.data.remote

import com.example.photos.data.remote.dto.AlbumDto
import retrofit2.http.GET


interface PlaceHolderApi {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
}