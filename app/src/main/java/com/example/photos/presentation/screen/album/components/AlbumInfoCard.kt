package com.example.photos.presentation.screen.album.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photos.domain.entity.AlbumItemEntity
import com.example.photos.presentation.screen.album.mockPhotoItems
import com.skydoves.landscapist.glide.GlideImage

/**
 * Composable function to display information about an album item.
 *
 * @param albumItem The album item entity containing the photo and related data.
 * @param modifier Optional Modifier to apply additional styling or layout constraints.
 */
@Composable
fun AlbumInfoCard(albumItem: AlbumItemEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            GlideImage(
                imageModel = albumItem.photo?.thumbnailUrl ?: "",
                contentDescription = albumItem.photo?.title ?: "--",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = albumItem.photo?.title ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                Text(
                    text = albumItem.album.title,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                Text(
                    text = albumItem.user?.username ?: "",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumInfoCardPreview() {
    AlbumInfoCard(albumItem = mockPhotoItems.first())
}
