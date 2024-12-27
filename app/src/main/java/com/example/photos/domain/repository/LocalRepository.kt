package com.example.photos.domain.repository

import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing local data.
 */
interface LocalRepository {
    /**
     * Retrieves all albums from the cache as a flow of lists.
     *
     * @return A flow of lists containing all albums from the cache.
     */
    fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>>

    /**
     * Inserts or replaces a list of album cache entities into the database.
     *
     * @param albumsCache The list of album cache entities to insert or replace.
     */
    suspend fun insertAlbumsToCache(albumsCache: List<AlbumItemEntity>)

    /**
     * Clears all data from the album cache.
     */
    suspend fun clearCache()
}
