package com.sryang.addreview.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sryang.addreview.R
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.data.testSelectRestaurantData
import com.sryang.addreview.usecase.SelectRestaurantUseCase
import com.sryang.addreview.viewmodels.SelectRestaurantViewModel

@Composable
fun SelectRestaurant(
    viewModel: SelectRestaurantViewModel,           // 음식점 추가 뷰모델
    onRestaurant: (SelectRestaurantData) -> Unit,   // 음식점 클릭
    onClose: () -> Unit, onRefresh: () -> Unit      // 닫기 클릭
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
    ) {
        SelectRestaurantTitleBar(onClose, onRefresh)
        SearchBar()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp), content = {
            items(uiState.restaurantList.size) {
                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .clickable {
                            onRestaurant.invoke(uiState.restaurantList[it])
                        },
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.restaurantList[it].restaurantName,
                        maxLines = 1,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = uiState.restaurantList[it].address,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        })
    }
}

@Composable
fun SelectRestaurantTitleBar(onClose: () -> Unit, onRefresh: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_close1),
            contentDescription = "",
            Modifier
                .width(30.dp)
                .clickable { onClose.invoke() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "Select a restaurant", fontSize = 20.sp, modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = "",
            Modifier
                .width(20.dp)
                .clickable { onRefresh.invoke() }
        )
    }
}

@Composable
fun SearchBar() {
    Row(
        Modifier.padding(start = 8.dp, end = 8.dp),
    ) {
        var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                // it is crucial that the update is fed back into BasicTextField in order to
                // see updates on the text
                //value = it
                value = it
            },
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Box(Modifier.weight(1f)) {
                        innerTextField()
                        if (value.text.isEmpty())
                            Text(
                                text = "Write a restaurant",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewSelectRestaurant() {
    SelectRestaurant(viewModel = SelectRestaurantViewModel(
        object : SelectRestaurantUseCase {
            override suspend fun getRestaurant(): List<SelectRestaurantData> {
                return ArrayList<SelectRestaurantData>().apply {
                    add(testSelectRestaurantData())
                    add(testSelectRestaurantData())
                    add(testSelectRestaurantData())
                    add(testSelectRestaurantData())
                    add(testSelectRestaurantData())
                }
            }
        }
    ), onRestaurant = {}, onClose = { /*TODO*/ }) {

    }
}