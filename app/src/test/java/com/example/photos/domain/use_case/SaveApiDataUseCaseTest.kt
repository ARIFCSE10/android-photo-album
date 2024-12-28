package com.example.photos.domain.use_case

import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveApiDataUseCaseTest {

    private lateinit var saveApiDataUseCase: SaveApiDataUseCase
    private val localRepository: LocalRepository = mock()

    @Before
    fun setup() {
        saveApiDataUseCase = SaveApiDataUseCase(localRepository)
    }

    @Test
    fun saveApiData() = runTest {
        // Mock data
        val album = Album(1, 1, "Test Album")
        val user = User(1, "Test User", "test@test.com")
        val albumItemEntityList = listOf(AlbumItemEntity(1, album, user, null))

        // Invoke the use case
        saveApiDataUseCase(albumItemEntityList).collect {}

        // Verify that insertAlbumsToCache() is called with the correct data
        Mockito.verify(localRepository).insertAlbumsToCache(albumItemEntityList)
    }
}
