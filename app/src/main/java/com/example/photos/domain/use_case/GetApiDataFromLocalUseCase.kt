package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for retrieving API data from local storage.
 */
class GetApiDataFromLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {
    /**
     * Retrieves API data from local storage.
     *
     * @return A flow emitting a Resource indicating success or failure.
     */
    operator fun invoke(): Flow<Resource<List<AlbumItemEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val albums = localRepository.getAlbumsFromCache().first()
            emit(Resource.Success(albums))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Something went wrong"))
        }
    }
}
