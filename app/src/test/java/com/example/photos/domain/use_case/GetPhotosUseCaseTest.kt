package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.AlbumDto
import com.example.photos.data.remote.dto.PhotoDto
import com.example.photos.domain.model.Photo
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

class GetPhotosUseCaseTest {

    private lateinit var getPhotoUseCase: GetPhotoUseCase
    private val remoteRepository: RemoteRepository = mock()

    @Before
    fun setup() {
        getPhotoUseCase = GetPhotoUseCase(remoteRepository)
    }

    @Test
    fun getPhotos_success() = runTest {
        // Mock successful response
        val photoDto = PhotoDto(1, 1, "Test Title", "testUrl", "testThumbnailUrl")
        val photo = Photo(1, 1, "Test Title", "testUrl", "testThumbnailUrl")
        Mockito.`when`(remoteRepository.getPhotos(1)).thenReturn(
            listOf(photoDto)
        )

        // Invoke the use case
        val result = getPhotoUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Success)
        assertEquals(photo, result.data)
    }

    @Test
    fun getPhotos_httpError() = runTest {
        // Mock HTTP error
        val errorCode = 404
        Mockito.`when`(remoteRepository.getPhotos(1)).thenThrow(
            HttpException(
                retrofit2.Response.error<List<AlbumDto>>(
                    errorCode, ResponseBody.create(null, "")
                )
            )
        )

        // Invoke the use case
        val result = getPhotoUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertTrue(result.message?.contains(errorCode.toString()) ?: false)
    }

    @Test
    fun getPhotos_ioError() = runTest {
        // Mock IO error
        val error = "IO Error"
        Mockito.`when`(remoteRepository.getPhotos(albumId = 1)).thenAnswer {
            throw IOException(error)
        }

        // Invoke the use case
        val result = getPhotoUseCase(1).first()

        // Verify the result
        assertTrue(result is Resource.Error)
        assertEquals(error, result.message)
    }
}
