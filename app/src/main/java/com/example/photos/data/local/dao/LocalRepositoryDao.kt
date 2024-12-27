package com.example.photos.data.local.dao

import androidx.room.*
import com.example.photos.domain.entity.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalRepositoryDao {
    @Query("SELECT * from album_cache")
    fun getAlbumsFromCache(): Flow<List<AlbumItemEntity>>

    @Query("SELECT * from album_cache WHERE id = :id")
    fun getAlbumCacheEntity(id: Int): Flow<AlbumItemEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumCacheEntity(albumCache: List<AlbumItemEntity>)

    @Query("DELETE FROM album_cache")
    suspend fun clearCache()
}