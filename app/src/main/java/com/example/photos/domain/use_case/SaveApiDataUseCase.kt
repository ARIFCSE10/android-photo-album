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


class SaveApiDataUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {
    operator fun invoke(albums: List<AlbumItemEntity>): Flow<Resource<Unit>> = flow {
        try {
            localRepository.clearCache()
            localRepository.insertAlbumsToCache(albums)
        } catch (_: Exception) {
        }
    }
}