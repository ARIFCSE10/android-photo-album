package com.example.photos.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.Photo
import com.example.photos.domain.model.User

@Entity(tableName = "album_cache")
data class AlbumItemEntity(
    @PrimaryKey val id: Int,
    val album: Album,
    val user: User?,
    val photo: Photo?,
)
