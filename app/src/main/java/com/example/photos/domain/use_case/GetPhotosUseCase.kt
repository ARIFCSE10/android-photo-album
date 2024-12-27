package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.toPhoto
import com.example.photos.domain.model.Photo
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving photos.
 */
class GetPhotoUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    /**
     * Retrieves a photo by album ID.
     *
     * @param albumId The ID of the album to retrieve photos for.
     * @return A flow emitting a Resource indicating success or failure.
     */
    operator fun invoke(albumId: Int): Flow<Resource<Photo>> = flow {
        try {
            val photo = repository.getPhotos(albumId).first().toPhoto()
            emit(Resource.Success(photo))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error getting data"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Internet connection error"))
        }
    }
}
