package com.example.photos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photos.data.local.dao.LocalRepositoryDao
import com.example.photos.domain.entity.AlbumItemEntity


@Database(
    entities = [AlbumItemEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class LocalCacheDB : RoomDatabase() {
    abstract val localRepositoryDao: LocalRepositoryDao

    companion object {
        const val DATABASE_NAME = "album_db"
    }
}