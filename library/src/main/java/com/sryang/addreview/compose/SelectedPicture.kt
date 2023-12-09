package com.sryang.addreview.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

@Composable
fun SelectedPicture(
    list: List<String> /*선택된 이미지*/,
    onDelete: (String) -> Unit,
    onAddPicture: (() -> Unit)? = null
) {
    val size = list.size + 1
    Row(Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
        LazyRow(content = {
            items(size) {
                val interactionSource = remember { MutableInteractionSource() }
                if (it < size - 1) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(list[it])
                                .build(),
                            contentDescription = "",
                            modifier = Modifier
                                .size(ImageSize)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { onDelete.invoke(list[it]) }
                        )
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                } else {
                    Button(
                        onClick = { onAddPicture?.invoke() },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.size(ImageSize)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }
            }
        })
    }
}

@Preview
@Composable
fun PreviewSelectedPicture() {
    SelectedPicture(list = ArrayList<String>().apply {
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
    }, onDelete = {})
}