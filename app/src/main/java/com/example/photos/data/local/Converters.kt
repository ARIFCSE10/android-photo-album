package com.example.photos.data.local

import androidx.room.TypeConverter
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.Photo
import com.example.photos.domain.model.User
import com.google.gson.Gson

/**
 * Converts between domain model objects and JSON strings using Gson.
 */
class Converters {
    private val gson = Gson()

    /**
     * Converts an Album object to a JSON string.
     *
     * @param album The Album object to convert.
     * @return A JSON string representing the Album object, or null if the input is null.
     */
    @TypeConverter
    fun fromAlbum(album: Album?): String? {
        return album?.let { gson.toJson(it) }
    }

    /**
     * Converts a JSON string to an Album object.
     *
     * @param albumString The JSON string representing the Album object.
     * @return An Album object, or null if the input is null.
     */
    @TypeConverter
    fun toAlbum(albumString: String?): Album? {
        return albumString?.let { gson.fromJson(it, Album::class.java) }
    }

    /**
     * Converts a User object to a JSON string.
     *
     * @param user The User object to convert.
     * @return A JSON string representing the User object, or null if the input is null.
     */
    @TypeConverter
    fun fromUser(user: User?): String? {
        return user?.let { gson.toJson(it) }
    }

    /**
     * Converts a JSON string to a User object.
     *
     * @param userString The JSON string representing the User object.
     * @return A User object, or null if the input is null.
     */
    @TypeConverter
    fun toUser(userString: String?): User? {
        return userString?.let { gson.fromJson(it, User::class.java) }
    }

    /**
     * Converts a Photo object to a JSON string.
     *
     * @param photo The Photo object to convert.
     * @return A JSON string representing the Photo object, or null if the input is null.
     */
    @TypeConverter
    fun fromPhoto(photo: Photo?): String? {
        return photo?.let { gson.toJson(it) }
    }

    /**
     * Converts a JSON string to a Photo object.
     *
     * @param photoString The JSON string representing the Photo object.
     * @return A Photo object, or null if the input is null.
     */
    @TypeConverter
    fun toPhoto(photoString: String?): Photo? {
        return photoString?.let { gson.fromJson(it, Photo::class.java) }
    }
}
