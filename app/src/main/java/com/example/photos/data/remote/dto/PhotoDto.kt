package com.example.photos.data.remote.dto

import com.example.photos.domain.model.Photo
import com.google.gson.annotations.SerializedName


data class PhotoDto(
    @SerializedName("albumId") val albumId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String
)

fun PhotoDto.toPhoto(): Photo {
    return Photo(
        albumId = albumId,
        id = id,
        url = url,
        thumbnailUrl = thumbnailUrl,
        title = title
    )
}