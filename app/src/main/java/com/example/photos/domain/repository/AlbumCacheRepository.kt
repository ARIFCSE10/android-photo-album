package com.example.photos.domain.repository

import com.example.photos.domain.entity.AlbumCacheEntity
import kotlinx.coroutines.flow.Flow

interface AlbumCacheRepository {
    fun getAlbumsFromCache(): Flow<List<AlbumCacheEntity>>
}