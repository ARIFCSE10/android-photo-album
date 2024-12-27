package com.example.photos.data.remote

import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface for interacting with the Placeholder API.
 */
interface PlaceHolderApi {
    /**
     * Retrieves a list of albums from the Placeholder API.
     *
     * @return A list of AlbumDto objects representing the retrieved albums.
     */
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    /**
     * Retrieves a list of users from the Placeholder API.
     *
     * @return A list of UserDto objects representing the retrieved users.
     */
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    /**
     * Retrieves a list of photos from the Placeholder API for a specific album.
     *
     * @param albumId The ID of the album to retrieve photos for.
     * @return A list of PhotoDto objects representing the retrieved photos.
     */
    @GET("photos")
    suspend fun getPhotos(
        @Query("albumId")
        albumId: Int
    ): List<PhotoDto>
}
