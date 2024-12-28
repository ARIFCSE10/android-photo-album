package com.example.photos.domain.use_case

import com.example.photos.domain.repository.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ClearLocalApiDataUseCaseTest {

    private lateinit var clearLocalApiDataUseCase: ClearLocalApiDataUseCase
    private val localRepository: LocalRepository = mock()

    @Before
    fun setup() {
        clearLocalApiDataUseCase = ClearLocalApiDataUseCase(localRepository)
    }

    @Test
    fun clearLocalApiData() = runTest {
        // Invoke the use case
        clearLocalApiDataUseCase().collect {}

        // Verify that clearCache() is called
        Mockito.verify(localRepository).clearCache()
    }
}
