package com.example.photos.data.local.dao

import androidx.room.*
import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for accessing local database.
 */
@Dao
interface LocalRepositoryDao {
    /**
     * Retrieves all albums from the cache as a flow of lists.
     *
     * @return A flow of lists containing all albums from the cache.
     */
    @Query("SELECT * from album_cache")
    fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>>

    /**
     * Retrieves a specific album from the cache by its ID as a flow.
     *
     * @param id The ID of the album to retrieve.
     * @return A flow containing the specific album from the cache, or null if not found.
     */
    @Query("SELECT * from album_cache WHERE id = :id")
    fun getAlbumCacheEntity(id: Int): Flow<AlbumItemEntity?>

    /**
     * Inserts or replaces a list of album cache entities into the database.
     *
     * @param albumCache The list of album cache entities to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumCacheEntity(albumCache: List<AlbumItemEntity>)

    /**
     * Clears all data from the album cache.
     */
    @Query("DELETE FROM album_cache")
    suspend fun clearCache()
}
