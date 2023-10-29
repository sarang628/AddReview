package com.sryang.addreview.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sryang.addreview.R
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.viewmodels.SelectRestaurantViewModel

@Composable
fun SelectRestaurant(
    viewModel: SelectRestaurantViewModel,
    color: Color = Color(0xFFFFFBE6),
    onRestaurant: (SelectRestaurantData) -> Unit,
    onClose: () -> Unit, onRefresh: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .background(color)
    ) {
        TitleBar(onClose, onRefresh)
        Spacer(modifier = Modifier.height(20.dp))
        SearchBar()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp), content = {
            items(uiState.restaurantList.size) {
                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .clickable {
                            onRestaurant.invoke(uiState.restaurantList[it])
                        },
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = uiState.restaurantList[it].restaurantName, fontSize = 18.sp)
                    Text(
                        text = uiState.restaurantList[it].address,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        })
    }
}

@Composable
fun TitleBar(onClose: () -> Unit, onRefresh: () -> Unit) {
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
                    .clickable { onClose.invoke() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Select a restaurant", fontSize = 20.sp, modifier = Modifier.weight(1f))
            AsyncImage(
                model = R.drawable.ic_refresh,
                contentDescription = "",
                Modifier
                    .width(26.dp)
                    .clickable { onRefresh.invoke() }
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