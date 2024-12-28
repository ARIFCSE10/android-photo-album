package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.domain.model.Photo
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

class GetPhotosUseCaseTest {

    private lateinit var getPhotosUseCase: GetPhotoUseCase
    private val remoteRepository: RemoteRepository = mock()

    @Before
    fun setup() {
        getPhotosUseCase = GetPhotoUseCase(remoteRepository)
    }

    @Test
    fun getPhotos_success() = runTest {
        // Mock successful response
        val photoDto = PhotoDto(1, 1, "Test Title", "testUrl", "testThumbnailUrl")
        val photo = Photo(1, 1, "Test Title", "testUrl", "testThumbnailUrl")
        Mockito.`when`(remoteRepository.getPhotos(1)).thenReturn(flow { emit(listOf(photoDto)) })

        // Invoke the use case
        val result = getPhotosUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertEquals(photo, result.data)
    }

    @Test
    fun getPhotos_httpError() = runTest {
        // Mock HTTP error
        val errorMessage = "HTTP Error"
        Mockito.`when`(remoteRepository.getPhotos(1)).thenReturn(flow { throw HttpException(retrofit2.Response.error<List<PhotoDto>>(404, okhttp3.ResponseBody.create(okhttp3.MediaType.get("application/json"), ""))) })

        // Invoke the use case
        val result = getPhotosUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }

    @Test
    fun getPhotos_ioError() = runTest {
        // Mock IO error
        val errorMessage = "IO Error"
        Mockito.`when`(remoteRepository.getPhotos(1)).thenReturn(flow { throw IOException(errorMessage) })

        // Invoke the use case
        val result = getPhotosUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, result.message)
    }
}
