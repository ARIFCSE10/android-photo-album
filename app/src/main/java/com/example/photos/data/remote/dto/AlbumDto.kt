package com.example.photos.data.remote.dto

import com.example.photos.domain.model.Album
import com.google.gson.annotations.SerializedName


data class AlbumDto(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)

fun AlbumDto.toAlbum(): Album {
    return Album(
        userId = userId,
        id = id,
        title = title
    )
}

