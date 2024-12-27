package com.example.photos.presentation.screen.album

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photos.common.Resource
import com.example.photos.domain.use_case.GetAlbumUseCase
import com.example.photos.domain.entity.AlbumCacheEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val getAlbumUseCase: GetAlbumUseCase
) : ViewModel() {

    private val _state = mutableStateOf(AlbumListState())
    val state: State<AlbumListState> = _state


    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            getAlbumUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = AlbumListState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _state.value = AlbumListState(
                            error = true,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Success -> {
                        _state.value = AlbumListState(albums = result.data?.map {
                            AlbumCacheEntity(
                                id = it.id,
                                album = it,
                                user = null,
                                photo = null,
                            )
                        } ?: emptyList())
                    }
                }

            }
        }
    }

}