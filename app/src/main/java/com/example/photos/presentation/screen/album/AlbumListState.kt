package com.example.photos.presentation.screen.album

import com.example.photos.domain.entity.AlbumItemEntity

/**
 * Data class to represent the state of the album list screen.
 *
 * @property isLoading Indicates whether the data is currently being loaded.
 * @property error Indicates whether an error occurred while fetching data.
 * @property errorMessage The error message if an error occurred.
 * @property albums The list of album items fetched from the data source.
 */
data class AlbumListState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String = "",
    val albums: List<AlbumItemEntity> = emptyList(),
)
