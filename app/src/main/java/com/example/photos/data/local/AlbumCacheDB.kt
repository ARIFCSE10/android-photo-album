package com.example.photos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photos.data.local.dao.AlbumCacheDao
import com.example.photos.domain.entity.AlbumCacheEntity


@Database(
    entities = [AlbumCacheEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AlbumCacheDB : RoomDatabase() {
    abstract val albumCacheDao: AlbumCacheDao

    companion object {
        const val DATABASE_NAME = "album_db"
    }
}