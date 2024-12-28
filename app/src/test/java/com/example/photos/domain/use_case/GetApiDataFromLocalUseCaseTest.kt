package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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
        Mockito.`when`(localRepository.getAlbumsFromCache()).thenReturn(flow { emit(albumItemEntityList) })

        // Invoke the use case
        val result = getApiDataFromLocalUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertEquals(albumItemEntityList, result.data)
    }

    @Test
    fun getApiDataFromLocal_emptyData() = runTest {
        // Mock successful response with empty data
        Mockito.`when`(localRepository.getAlbumsFromCache()).thenReturn(flow { emit(emptyList()) })

        // Invoke the use case
        val result = getApiDataFromLocalUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals("No data found", result.message)
    }

    @Test
    fun getApiDataFromLocal_error() = runTest {
        // Mock error
        val errorMessage = "Something went wrong"
        Mockito.`when`(localRepository.getAlbumsFromCache()).thenReturn(flow { throw Exception(errorMessage) })

        // Invoke the use case
        val result = getApiDataFromLocalUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }
}
