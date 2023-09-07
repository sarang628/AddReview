package com.sryang.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AddReviewScreen(
    list: List<String>,
    onShare: (AddReviewData) -> Unit,
    onBack: (Void?) -> Unit
) {
    var input by remember { mutableStateOf("") }
    Column(
        Modifier
            //.background(Color.DarkGray)
            .fillMaxHeight()
    ) {
        // titlebar
        TitleBar(onShare = {
            onShare.invoke(
                AddReviewData(pictures = list, contents = input)
            )
        }, onBack = onBack)
        // selected picture
        SelectedPicture(list = list)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "",
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        // select restaurant
        SelectRestaurant()
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "",
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        // Write a caption
        WriteCaption(input = input, onValueChange = {
            input = it
        })
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "",
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
    }
}

@Composable
fun TitleBar(
    onBack: (Void?) -> Unit,
    onShare: (Void?) -> Unit
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter =
            painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            modifier = Modifier
                .height(30.dp)
                .clickable {
                    onBack.invoke(null)
                }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "New post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            Text(text = "share",
                color = Color(0xFF4193EF),
                modifier = Modifier.clickable {
                    onShare.invoke(null)
                }
            )
        }
    }
}

val ImageSize = 100.dp

@Composable
fun SelectedPicture(list: List<String>) {
    Row(Modifier.padding(top = 8.dp, start = 12.dp)) {
        LazyRow(content = {
            items(list.size) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(list[it])
                        .build(),
                    contentDescription = "",
                    modifier = Modifier.size(ImageSize),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(3.dp))
            }
        })
    }
}

@Preview
@Composable
fun SelectRestaurant() {
    Row(
        Modifier
            .height(50.dp)
            .padding(start = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add restaurant")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteCaption(input: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = input,
        placeholder = {
            Text(text = "Write a caption", color = Color.Gray, fontSize = 14.sp)
        },
        onValueChange = {
            onValueChange.invoke(it)
        },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}