package com.example.photos.data.local

import androidx.room.TypeConverter
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.Photo
import com.example.photos.domain.model.User
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAlbum(album: Album?): String? {
        return album?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAlbum(albumString: String?): Album? {
        return albumString?.let { gson.fromJson(it, Album::class.java) }
    }

    @TypeConverter
    fun fromUser(user: User?): String? {
        return user?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toUser(userString: String?): User? {
        return userString?.let { gson.fromJson(it, User::class.java) }
    }

    @TypeConverter
    fun fromPhoto(photo: Photo?): String? {
        return photo?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toPhoto(photoString: String?): Photo? {
        return photoString?.let { gson.fromJson(it, Photo::class.java) }
    }
}
