//noinspection UsingMaterialAndMaterial3Libraries
package com.example.photos.presentation.screen.album.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composable function to display the top bar for album screens.
 *
 * @param modifier Optional Modifier to apply additional styling or layout constraints.
 */
@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(elevation = 8.dp) {
        Text(
            "Albums",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
