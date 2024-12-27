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


class GetPhotoUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
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