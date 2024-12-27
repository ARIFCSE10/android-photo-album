package com.example.photos.data.repository

import com.example.photos.data.remote.PlaceHolderApi
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.data.remote.dto.UserDto
import com.example.photos.domain.repository.RemoteRepository
import javax.inject.Inject

/**
 * Implementation of the RemoteRepository interface using remote API.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val api: PlaceHolderApi
) : RemoteRepository {
    /**
     * Retrieves a list of albums from the remote API.
     *
     * @return A list of AlbumDto objects representing the retrieved albums.
     */
    override suspend fun getAlbums(): List<AlbumDto> {
        return api.getAlbums()
    }

    /**
     * Retrieves a list of users from the remote API.
     *
     * @return A list of UserDto objects representing the retrieved users.
     */
    override suspend fun getUsers(): List<UserDto> {
        return api.getUsers()
    }

    /**
     * Retrieves a list of photos from the remote API for a specific album.
     *
     * @param albumId The ID of the album to retrieve photos for.
     * @return A list of PhotoDto objects representing the retrieved photos.
     */
    override suspend fun getPhotos(albumId: Int): List<PhotoDto> {
        return api.getPhotos(albumId)
    }
}
