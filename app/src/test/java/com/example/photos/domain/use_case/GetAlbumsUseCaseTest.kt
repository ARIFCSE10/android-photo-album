package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.domain.model.Album
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.first
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

class GetAlbumsUseCaseTest {

    private lateinit var getAlbumsUseCase: GetAlbumsUseCase
    private val remoteRepository: RemoteRepository = mock()

    @Before
    fun setup() {
        getAlbumsUseCase = GetAlbumsUseCase(remoteRepository)
    }

    @Test
    fun getAlbums_success() = runTest {
        // Mock successful response
        val albumDtoList = listOf(AlbumDto(1, 1, "Test Album"))
        val albumList = listOf(Album(1, 1, "Test Album"))
        Mockito.`when`(remoteRepository.getAlbums()).thenReturn(
            albumDtoList
        )

        // Invoke the use case
        val result = getAlbumsUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertEquals(albumList, result.data)
    }

    @Test
    fun getAlbums_httpError() = runTest {
        // Mock HTTP error
        val errorMessage = "HTTP Error"
        Mockito.`when`(remoteRepository.getAlbums()).thenAnswer {
            throw HttpException(
                retrofit2.Response.error<List<AlbumDto>>(
                    404, ResponseBody.create(null, "")
                )
            )
        }


        // Invoke the use case
        val result = getAlbumsUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun getAlbums_ioError() = runTest {
        // Mock IO error
        val errorMessage = "IO Error"
        Mockito.`when`(remoteRepository.getAlbums()).thenThrow(
            IOException(errorMessage)
        )

        // Invoke the use case
        val result = getAlbumsUseCase().first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }
}
