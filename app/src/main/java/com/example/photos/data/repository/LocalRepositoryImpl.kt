package com.example.photos.data.repository

import com.example.photos.data.local.dao.LocalRepositoryDao
import com.example.photos.domain.repository.LocalRepository
import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the LocalRepository interface using local database.
 */
class LocalRepositoryImpl(
    private val dao: LocalRepositoryDao
) : LocalRepository {
    /**
     * Retrieves all albums from the cache as a flow of lists.
     *
     * @return A flow of lists containing all albums from the cache.
     */
    override fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>> {
        return dao.getAlbumsFromCache()
    }

    /**
     * Inserts or replaces a list of album cache entities into the database.
     *
     * @param albumsCache The list of album cache entities to insert or replace.
     */
    override suspend fun insertAlbumsToCache(albumsCache: List<AlbumItemEntity>) {
        return dao.insertAlbumCacheEntity(albumsCache)
    }

    /**
     * Clears all data from the album cache.
     */
    override suspend fun clearCache() {
        return dao.clearCache()
    }
}
