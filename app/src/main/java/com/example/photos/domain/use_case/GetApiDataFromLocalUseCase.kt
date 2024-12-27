package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.toAlbum
import com.example.photos.data.remote.dto.toUser
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.repository.LocalRepository
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetApiDataFromLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {
    operator fun invoke(): Flow<Resource<List<AlbumItemEntity>>> = flow {
        try {
            emit(Resource.Loading())
            localRepository.getAlbumsFromCache().collect {
                if (it.isEmpty()) {
                    emit(Resource.Error(message = "No data found"))
                } else {
                    emit(Resource.Success(it))
                }
            }
        } catch (_: Exception) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }
}