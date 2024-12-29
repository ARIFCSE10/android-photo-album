package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.UserDto
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
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
        Mockito.`when`(remoteRepository.getUsers()).thenReturn(
            userDtoList
        )

        // Invoke the use case
        val result = getUsersUseCase().drop(1).first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertTrue { userList.count() == result.data?.count() }
        assertTrue { userList.first().id == result.data?.first()?.id }
    }

    @Test
    fun getUsers_httpError() = runTest {
        // Mock HTTP error
        val errorCode = 404
        Mockito.`when`(remoteRepository.getUsers()).thenThrow(
            HttpException(
                retrofit2.Response.error<List<AlbumDto>>(
                    errorCode, ResponseBody.create(null, "")
                )
            )
        )

        // Invoke the use case
        val result = getUsersUseCase().drop(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertTrue(result.message?.contains(errorCode.toString()) ?: false)
    }

    @Test
    fun getUsers_ioError() = runTest {
        // Mock IO error
        val errorMessage = "IO Error"
        Mockito.`when`(remoteRepository.getUsers()).thenAnswer {
            throw IOException(errorMessage)
        }

        // Invoke the use case
        val result = getUsersUseCase().drop(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }
}
