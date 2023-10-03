package com.sryang.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SelectRestaurant(
    color: Color = Color(0xFFFFFBE6)
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color)
    ) {
        TitleBar()
        Spacer(modifier = Modifier.height(20.dp))
        SearchBar()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp), content = {
            items(20) {
                Column(
                    modifier = Modifier.height(60.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "McDonald", fontSize = 18.sp)
                    Text(text = "Seoul", fontSize = 16.sp, color = Color.Gray)
                }
            }
        })
    }
}

@Composable
fun TitleBar() {
    Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
        //title
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.ic_close1,
                contentDescription = "",
                Modifier
                    .width(30.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Select a restaurant", fontSize = 20.sp, modifier = Modifier.weight(1f))
            AsyncImage(
                model = R.drawable.ic_refresh,
                contentDescription = "",
                Modifier
                    .width(26.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    Row(
        Modifier
            .padding(start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        Row(
            Modifier
                .background(Color(0xFFDEDEDE))
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.ic_search,
                contentDescription = "",
                Modifier.size(20.dp)
            )
            OutlinedTextField(
                value = "",
                placeholder = {
                    androidx.compose.material3.Text(
                        text = "Write a caption",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                onValueChange = {

                },
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            AsyncImage(
                model = R.drawable.ic_close,
                contentDescription = "",
                Modifier.size(20.dp)
            )
        }
    }
}