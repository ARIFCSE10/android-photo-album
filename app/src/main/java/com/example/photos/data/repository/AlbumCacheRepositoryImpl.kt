package com.example.photos.data.repository

import com.example.photos.data.local.dao.AlbumCacheDao
import com.example.photos.domain.repository.AlbumCacheRepository
import com.example.photos.domain.entity.AlbumCacheEntity
import kotlinx.coroutines.flow.Flow

class AlbumCacheRepositoryImpl(
    private val dao: AlbumCacheDao
) : AlbumCacheRepository {
    override fun getAlbumsFromCache(): Flow<List<AlbumCacheEntity>> {
        return dao.getAlbumsCache()
    }

}