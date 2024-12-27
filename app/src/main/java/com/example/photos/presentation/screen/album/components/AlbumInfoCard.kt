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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.photos.domain.entity.AlbumItemEntity
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun AlbumInfoCard(photo: AlbumItemEntity, modifier: Modifier = Modifier) {
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
                imageModel = photo.photo?.thumbnailUrl ?: "",
                contentDescription = photo.photo?.title ?: "--",
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
                    text = photo.photo?.title ?: "",
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = photo.album.title,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = photo.user?.username ?: "",
                )
            }
        }
    }
}