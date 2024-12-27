package com.example.photos.data.local.dao

import androidx.room.*
import com.example.photos.domain.entity.AlbumCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumCacheDao {
    @Query("SELECT * from album_cache")
    fun getAlbumsCache(): Flow<List<AlbumCacheEntity>>

    @Query("SELECT * from album_cache WHERE id = :id")
    fun getAlbumCacheEntity(id: Int): Flow<AlbumCacheEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumCacheEntity(albumCache: List<AlbumCacheEntity>)
}