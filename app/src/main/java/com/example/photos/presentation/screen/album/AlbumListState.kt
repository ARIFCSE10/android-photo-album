package com.example.photos.presentation.screen.album

import com.example.photos.domain.entity.AlbumCacheEntity

data class AlbumListState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = "",
    val albums: List<AlbumCacheEntity> = mockPhotoItems,
)