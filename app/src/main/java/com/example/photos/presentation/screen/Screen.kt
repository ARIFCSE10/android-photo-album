package com.example.photos.presentation.screen

/**
 * Represents a screen in the application.
 *
 * @property route The route associated with the screen.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the album list screen.
     */
    data object AlbumListScreen : Screen("albumListScreen")
}
