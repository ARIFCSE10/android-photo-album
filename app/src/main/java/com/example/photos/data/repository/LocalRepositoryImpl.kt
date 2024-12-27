package com.example.photos.data.repository

import com.example.photos.data.local.dao.LocalRepositoryDao
import com.example.photos.domain.repository.LocalRepository
import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val dao: LocalRepositoryDao
) : LocalRepository {
    override fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>> {
        return dao.getAlbumsFromCache()
    }

    override suspend fun insertAlbumsToCache(albumsCache: List<AlbumItemEntity>) {
        return dao.insertAlbumCacheEntity(albumsCache)
    }

    override suspend fun clearCache() {
        return dao.clearCache()
    }

}