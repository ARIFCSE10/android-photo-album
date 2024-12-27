package com.example.photos.domain.repository

import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>>
    suspend fun insertAlbumsToCache(albumsCache: List<AlbumItemEntity>)
    suspend fun clearCache()
}