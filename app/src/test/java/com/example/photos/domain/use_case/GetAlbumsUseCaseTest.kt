package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.domain.model.Album
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.drop
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
        val result = getAlbumsUseCase().drop(1).first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertTrue { albumList.count() == result.data?.count() }
        assertTrue { albumList.first().id == result.data?.first()?.id }
    }

    @Test
    fun getAlbums_httpError() = runTest {
        // Mock HTTP error
        val errorCode = 404
        Mockito.`when`(remoteRepository.getAlbums()).thenThrow(
            HttpException(
                retrofit2.Response.error<List<AlbumDto>>(
                    errorCode, ResponseBody.create(null, "")
                )
            )
        )


        // Invoke the use case
        val result = getAlbumsUseCase().drop(1).first()
        // Verify the result
        assertTrue(result is Resource.Error)
        assertTrue(result.message?.contains(errorCode.toString()) ?: false)
    }

    @Test
    fun getAlbums_ioError() = runTest {
        // Mock IO error
        val error = "IO Error"
        Mockito.`when`(remoteRepository.getAlbums()).thenAnswer {
            throw IOException(error)
        }


        // Invoke the use case
        val result = getAlbumsUseCase().drop(1).first()
        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(error, result.message)
    }
}
