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
 * Use case for saving API data to the local repository.
 *
 * @property localRepository The local repository to save data to.
 */
class SaveApiDataUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {
    /**
     * Invokes the use case to save API data.
     *
     * @param albums The list of album items to save.
     * @return A flow of Resource<Unit> representing the result of the operation.
     */
    operator fun invoke(albums: List<AlbumItemEntity>): Flow<Resource<Unit>> = flow {
        try {
            // Insert albums into the cache
            localRepository.insertAlbumsToCache(albums)
        } catch (_: Exception) {
        }
    }
}
