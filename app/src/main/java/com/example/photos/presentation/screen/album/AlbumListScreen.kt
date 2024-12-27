//noinspection UsingMaterialAndMaterial3Libraries
package com.example.photos.presentation.screen.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.photos.domain.entity.AlbumCacheEntity
import com.example.photos.domain.model.Album
import com.example.photos.domain.model.Photo
import com.example.photos.domain.model.User
import com.example.photos.presentation.screen.album.components.AlbumInfoCard
import com.example.photos.presentation.screen.album.components.TopBar
import com.example.photos.ui.theme.PhotosTheme

@Composable
fun AlbumListScreen(
    navController: NavController,
    viewModel: AlbumListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(modifier = Modifier) }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {

            //Page States
            if (state.isLoading) { // Loading Case
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = "Loading data",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.primary
                    )
                }

            } else if (state.error) {  // Error Case
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.h5
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    items(
                        state.albums.size,
                        key = { index -> index }) { index ->
                        AlbumInfoCard(photo = state.albums[index])
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PhotoListPreview() {
    PhotosTheme {
        AlbumListScreen(navController = NavController(LocalContext.current))
    }
}

internal val mockPhotoItems = listOf(
    AlbumCacheEntity(
        id = 1,
        photo = Photo(
            id = 1,
            title = "Test Photo 1",
            url = "https://picsum.photos/600",
            thumbnailUrl = "https://picsum.photos/100",
            albumId = 1,
        ),
        album = Album(
            title = "Test Album 1",
            id = 1,
            userId = 1,
        ),
        user = User(
            id = 1,
            name = "User Name 1",
            username = "useName1"
        )
    ),
    AlbumCacheEntity(
        id = 2,
        photo = Photo(
            id = 2,
            title = "Test Photo 2",
            url = "https://picsum.photos/600",
            thumbnailUrl = "https://picsum.photos/100",
            albumId = 2,
        ),
        album = Album(
            title = "Test Album 2",
            id = 2,
            userId = 2,
        ),
        user = User(
            id = 2,
            name = "User Name 2",
            username = "useName2"
        )
    ),
)
