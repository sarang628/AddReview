package com.sarang.torang.addreview.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sarang.torang.addreview.uistate.Picture
import kotlin.invoke
import kotlin.text.get

@Composable
fun SelectedPicture(modifier        : Modifier          = Modifier,
                    list            : List<String>      = listOf(),
                    onDelete        : (String) -> Unit  = {},
                    onAddPicture    : () -> Unit        = {}) {
    val size = list.size + 1
    Row(modifier = modifier) {
        LazyRow(content = {
            items(size) {
                if (it < size - 1) {
                    PictureViewer(url = list[it],
                                  onDelete = { onDelete.invoke(list[it]) })
                    Spacer(modifier = Modifier.width(3.dp))
                } else {
                    Box(modifier = Modifier.size(ImageSize + 20.dp)){
                        Button(
                            onClick = { onAddPicture.invoke() },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.size(ImageSize)
                                .align(Alignment.Center)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    }
                }
            }
        })
    }
}

@Composable
fun PictureViewer(url : String,
                  onDelete : ()->Unit = {}){
    Box(modifier = Modifier.size(ImageSize + 20.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .size(ImageSize)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Icon(
            modifier = Modifier.align(Alignment.TopEnd)
                                .size(24.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = CircleShape
                                )
                               .clickable(true, onClick = onDelete),
            imageVector = Icons.Default.Clear,
            contentDescription = "",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedPicture() {
    SelectedPicture(list = listOf(""))
}