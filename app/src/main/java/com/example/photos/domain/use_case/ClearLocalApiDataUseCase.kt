package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for clearing local API data.
 */
class ClearLocalApiDataUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {
    /**
     * Clears the cache of local API data.
     *
     * @return A flow emitting a Resource indicating success or failure.
     */
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            localRepository.clearCache()
        } catch (_: Exception) {
        }
    }
}
