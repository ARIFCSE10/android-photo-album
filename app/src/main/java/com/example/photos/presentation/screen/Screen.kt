package com.example.photos.presentation.screen


sealed class Screen(val route: String) {
    data object HomeScreen : Screen("homeScreen")
    data object AlbumListScreen : Screen("albumListScreen")
}