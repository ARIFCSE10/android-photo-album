package com.example.photos.presentation.screen.album

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photos.common.Resource
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.User
import com.example.photos.domain.use_case.ClearLocalApiDataUseCase
import com.example.photos.domain.use_case.GetAlbumsUseCase
import com.example.photos.domain.use_case.GetApiDataFromLocalUseCase
import com.example.photos.domain.use_case.GetPhotoUseCase
import com.example.photos.domain.use_case.GetUsersUseCase
import com.example.photos.domain.use_case.SaveApiDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to manage the state and logic of the album list screen.
 *
 * @property getAlbumsUseCase Use case to fetch albums from the remote repository.
 * @property getUserUseCase Use case to fetch users from the remote repository.
 * @property getPhotoUseCase Use case to fetch photos from the remote repository.
 * @property saveApiDataUseCase Use case to save API data to the local repository.
 * @property clearLocalApiDataUseCase Use case to clear local API data.
 * @property getApiDataFromLocalUseCase Use case to fetch API data from the local repository.
 */
@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getUserUseCase: GetUsersUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val saveApiDataUseCase: SaveApiDataUseCase,
    private val clearLocalApiDataUseCase: ClearLocalApiDataUseCase,
    private val getApiDataFromLocalUseCase: GetApiDataFromLocalUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(AlbumListState())
    val state: State<AlbumListState> = _state

    init {
        zipAlbumsWithUsers()
    }

    private fun fetchPhotosOfAlbum() {
        viewModelScope.launch {
            _state.value.albums.forEachIndexed { index, albumItem ->
                getPhotoUseCase(albumItem.album.id).collect { photoResult ->
                    when (photoResult) {
                        is Resource.Success -> {
                            val photo = photoResult.data
                            val updatedAlbumItem = albumItem.copy(photo = photo)
                            val updatedAlbums = _state.value.albums.toMutableList()
                            updatedAlbums[index] = updatedAlbumItem
                            _state.value = _state.value.copy(albums = updatedAlbums)
                            saveApiData()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun fetchLocalData() {
        viewModelScope.launch {
            getApiDataFromLocalUseCase().collect { localResult ->
                when (localResult) {
                    is Resource.Success -> {
                        _state.value = AlbumListState(albums = localResult.data ?: emptyList())
                    }

                    is Resource.Loading -> {
                        _state.value = AlbumListState(isLoading = true)
                    }

                    else -> {
                        _state.value = AlbumListState(
                            error = true,
                            errorMessage = localResult.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }
    }

    private fun saveApiData() {
        viewModelScope.launch {
            saveApiDataUseCase(_state.value.albums).collect {}
        }
    }

    private fun clearApiData() {
        viewModelScope.launch {
            clearLocalApiDataUseCase().collect {}
        }
    }

    private fun zipAlbumsWithUsers() {
        viewModelScope.launch {
            val combinedFlow =
                getAlbumsUseCase().zip(getUserUseCase()) { albumsResult, usersResult ->
                    when (albumsResult) {
                        is Resource.Loading -> {
                            _state.value = AlbumListState(isLoading = true)
                        }

                        is Resource.Error -> {
                            fetchLocalData()
                        }

                        is Resource.Success -> {
                            if (albumsResult.data?.isEmpty() == true || usersResult.data?.isEmpty() == true) {
                                fetchLocalData()
                            }

                            val albums: List<Album>? = albumsResult.data
                            val users: List<User>? = usersResult.data
                            val albumList: List<AlbumItemEntity>? = albums?.map { album ->
                                val user: User? = users?.find { album.userId == it.id }
                                AlbumItemEntity(
                                    id = album.id,
                                    album = album,
                                    user = user,
                                    photo = null,
                                )
                            }

                            _state.value = AlbumListState(albums = albumList ?: emptyList())
                            clearApiData()
                            fetchPhotosOfAlbum()
                        }
                    }
                }

            combinedFlow.collect {}
        }
    }

}
