package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetApiDataFromLocalUseCaseTest {

    private lateinit var getApiDataFromLocalUseCase: GetApiDataFromLocalUseCase
    private val localRepository: LocalRepository = mock()

    @Before
    fun setup() {
        getApiDataFromLocalUseCase = GetApiDataFromLocalUseCase(localRepository)
    }

    @Test
    fun getApiDataFromLocal_success() = runTest {
        // Mock successful response with data
        val album = Album(1, 1, "Test Album")
        val user = User(1, "Test User", "test@test.com")
        val albumItemEntityList = listOf(AlbumItemEntity(1, album, user, null))

        Mockito.`when`(localRepository.getAlbumsFromCache()).thenReturn(flow {
            emit(albumItemEntityList)
        })

        // Invoke the use case
        getApiDataFromLocalUseCase().drop(1).collect { result ->
            // Verify the result
            assertTrue(result is Resource.Success)
            assertTrue { albumItemEntityList.count() == result.data?.count() }
            assertTrue { albumItemEntityList.first().id == result.data?.first()?.id }
        }
    }

    @Test
    fun getApiDataFromLocal_emptyData() = runTest {
        // Mock successful response with empty data
        Mockito.`when`(localRepository.getAlbumsFromCache()).thenReturn(flow {
            emit(emptyList<AlbumItemEntity>())
        })

        // Invoke the use case
        getApiDataFromLocalUseCase().drop(1).collect { result ->
            // Verify the result
            assertTrue(result is Resource.Success)
            assertTrue(result.data?.isEmpty() ?: false)
        }

    }

    @Test
    fun getApiDataFromLocal_error() = runTest {
        // Mock error
        val errorMessage = "Something went wrong"
        Mockito.`when`(localRepository.getAlbumsFromCache())
            .thenReturn(flow { throw Exception(errorMessage) })

        // Invoke the use case
        val result = getApiDataFromLocalUseCase().drop(1).collect { result ->
            // Verify the result
            assertTrue(result is Resource.Error)
            assertEquals(errorMessage, result.message)
        }
    }
}
