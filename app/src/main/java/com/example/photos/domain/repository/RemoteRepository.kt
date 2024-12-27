package com.example.photos.domain.repository

import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto

/**
 * Interface for accessing remote data.
 */
interface RemoteRepository {
    /**
     * Retrieves a list of albums from the remote API.
     *
     * @return A list of AlbumDto objects representing the retrieved albums.
     */
    suspend fun getAlbums(): List<AlbumDto>

    /**
     * Retrieves a list of users from the remote API.
     *
     * @return A list of UserDto objects representing the retrieved users.
     */
    suspend fun getUsers(): List<UserDto>

    /**
     * Retrieves a list of photos from the remote API for a specific album.
     *
     * @param albumId The ID of the album to retrieve photos for.
     * @return A list of PhotoDto objects representing the retrieved photos.
     */
    suspend fun getPhotos(albumId: Int): List<PhotoDto>
}
