package com.example.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photos.common.Resource
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.Photo
import com.example.photos.domain.model.User
import com.example.photos.domain.use_case.ClearLocalApiDataUseCase
import com.example.photos.domain.use_case.GetAlbumsUseCase
import com.example.photos.domain.use_case.GetApiDataFromLocalUseCase
import com.example.photos.domain.use_case.GetPhotoUseCase
import com.example.photos.domain.use_case.GetUsersUseCase
import com.example.photos.domain.use_case.SaveApiDataUseCase
import com.example.photos.presentation.MainActivity
import com.example.photos.presentation.screen.album.AlbumListViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AlbumListViewModel
    private val getAlbumsUseCase: GetAlbumsUseCase = mock()
    private val getUsersUseCase: GetUsersUseCase = mock()
    private val getPhotoUseCase: GetPhotoUseCase = mock()
    private val saveApiDataUseCase: SaveApiDataUseCase = mock()
    private val clearLocalApiDataUseCase: ClearLocalApiDataUseCase = mock()
    private val getApiDataFromLocalUseCase: GetApiDataFromLocalUseCase = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = AlbumListViewModel(
            getAlbumsUseCase,
            getUsersUseCase,
            getPhotoUseCase,
            saveApiDataUseCase,
            clearLocalApiDataUseCase,
            getApiDataFromLocalUseCase
        )
    }


    @Test
    fun navigateToAlbumListScreen() = runTest {
        // Check if the AlbumListScreen is displayed initially
        composeTestRule.onNodeWithText("Albums").assertExists()

        // Mock successful API response
        val album = Album(1, 1, "Test Album")
        val user = User(1, "Test User", "test_user")
        val photo = Photo(
            albumId = 1, id = 1, title = "Test Photo", url = "", thumbnailUrl = ""
        )
        Mockito.`when`(getAlbumsUseCase()).thenReturn(flowOf(Resource.Success(listOf(album))))
        Mockito.`when`(getUsersUseCase()).thenReturn(flowOf(Resource.Success(listOf(user))))
        Mockito.`when`(getPhotoUseCase(albumId = album.id))
            .thenReturn(flowOf(Resource.Success(photo)))

        // Check if the list of cards is displayed
        composeTestRule.onNodeWithText(photo.title).assertExists()
        composeTestRule.onNodeWithText(album.title).assertExists()
        composeTestRule.onNodeWithText(user.username).assertExists()
    }

    @Test
    fun displayLoadingState() = runTest {
        // Mock loading state
        Mockito.`when`(getAlbumsUseCase()).thenReturn(flowOf(Resource.Loading()))
        Mockito.`when`(getUsersUseCase()).thenReturn(flowOf(Resource.Loading()))

        // Check if loading indicator is displayed
        composeTestRule.onNodeWithText("Loading").assertExists()
    }

    @Test
    fun displayErrorState() = runTest {
        // Mock error state
        val errorMessage = "An unexpected error occurred"
        Mockito.`when`(getAlbumsUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))
        Mockito.`when`(getUsersUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))

        // Check if error message is displayed
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun displayEmptyState() = runTest {
        // Mock empty state
        Mockito.`when`(getAlbumsUseCase()).thenReturn(flowOf(Resource.Success(emptyList())))
        Mockito.`when`(getUsersUseCase()).thenReturn(flowOf(Resource.Success(emptyList())))

        // Check if empty state message is displayed
        composeTestRule.onNodeWithText("No data available").assertExists()
    }

    @Test
    fun displayLocalDataState() = runTest {
        // Mock local data state
        val album = Album(1, 1, "Test Album")
        val user = User(1, "Test User", "test@test.com")
        val photo = Photo(
            albumId = 1, id = 1, title = "Test Photo", url = "", thumbnailUrl = ""
        )
        Mockito.`when`(getAlbumsUseCase()).thenReturn(flowOf(Resource.Error("Error")))
        Mockito.`when`(getUsersUseCase()).thenReturn(flowOf(Resource.Error("Error")))
        Mockito.`when`(getApiDataFromLocalUseCase()).thenReturn(
            flowOf(
                Resource.Success(
                    listOf(
                        com.example.photos.domain.entity.AlbumItemEntity(
                            1, album, user, photo,
                        )
                    )
                )
            )
        )

        // Check if local data is displayed
        composeTestRule.onNodeWithText(photo.title).assertExists()
        composeTestRule.onNodeWithText(album.title).assertExists()
        composeTestRule.onNodeWithText(user.username).assertExists()
    }
}
