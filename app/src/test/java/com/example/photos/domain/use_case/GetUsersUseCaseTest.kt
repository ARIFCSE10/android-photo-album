package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.UserDto
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val remoteRepository: RemoteRepository = mock()

    @Before
    fun setup() {
        getUsersUseCase = GetUsersUseCase(remoteRepository)
    }

    @Test
    fun getUsers_success() = runTest {
        // Mock successful response
        val userDtoList = listOf(UserDto(1, "Test User", "test@test.com"))
        val userList = listOf(User(1, "Test User", "test@test.com"))
        Mockito.`when`(remoteRepository.getUsers()).thenReturn(flow { emit(userDtoList) })

        // Invoke the use case
        val result = getUsersUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertEquals(userList, result.data)
    }

    @Test
    fun getUsers_httpError() = runTest {
        // Mock HTTP error
        val errorMessage = "HTTP Error"
        Mockito.`when`(remoteRepository.getUsers()).thenReturn(flow { throw HttpException(retrofit2.Response.error<List<UserDto>>(404, okhttp3.ResponseBody.create(okhttp3.MediaType.get("application/json"), ""))) })

        // Invoke the use case
        val result = getUsersUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun getUsers_ioError() = runTest {
        // Mock IO error
        val errorMessage = "IO Error"
        Mockito.`when`(remoteRepository.getUsers()).thenReturn(flow { throw IOException(errorMessage) })

        // Invoke the use case
        val result = getUsersUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }
}
