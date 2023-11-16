package com.sryang.addreview.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SelectedPicture(list: List<String> /*선택된 이미지*/) {
    Row(Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
        LazyRow(content = {
            items(list.size) {
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
                Spacer(modifier = Modifier.width(3.dp))
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
    })
}