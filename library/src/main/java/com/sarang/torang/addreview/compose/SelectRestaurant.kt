package com.sarang.torang.addreview.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.data.testSelectRestaurantData
import com.sarang.torang.addreview.uistate.SelectRestaurantUiState
import com.sarang.torang.addreview.usecase.SelectRestaurantUseCase
import com.sarang.torang.addreview.viewmodels.SelectRestaurantViewModel
import kotlin.text.get

/**
 * @param viewModel 음식점 선택 뷰모델
 * @param onRestaurant 음식점 클릭
 * @param onClose 닫기 클릭
 */
@Composable
fun SelectRestaurant(
    viewModel       : SelectRestaurantViewModel         = hiltViewModel(),
    onRestaurant    : (SelectRestaurantData) -> Unit    = {},
    onClose         : () -> Unit                        = {},
    onNotSelected   : () -> Unit                        = {}
) {
    val uiState : SelectRestaurantUiState by viewModel.uiState.collectAsState()
    Scaffold(topBar = {
        Column {
            SelectRestaurantTitleBar(onClose = onClose,
                                     onNotSelected = onNotSelected)
            SearchBar(keyword           = uiState.keyword,
                      onValueChange     = { viewModel.onValueChange(it) },
                      onDelete          = { viewModel.clearKeyword() })
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it)
                                      .fillMaxSize()){
            items(uiState.restaurantList.size) {
                RestaurantItem(
                    restaurantName  = uiState.restaurantList[it].restaurantName,
                    onRestaurant    = {onRestaurant.invoke(uiState.restaurantList[it])},
                    address         = uiState.restaurantList[it].address
                )
            }
        }
    }
}

@Composable
fun RestaurantItem(onRestaurant     : () -> Unit    = {},
                   restaurantName   : String        = "",
                   address          : String        = ""){
    Column(modifier            = Modifier.height(60.dp)
                                         .fillMaxWidth()
                                         .clickable { onRestaurant() },
           verticalArrangement = Arrangement.Center
    ) {
        Text(text       = restaurantName,
            maxLines   = 1,
            fontSize   = 18.sp,
            overflow   = TextOverflow.Ellipsis)
        Text(text       = address,
            fontSize   = 14.sp,
            color      = Color.Gray,
            overflow   = TextOverflow.Ellipsis,
            maxLines   = 1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRestaurantTitleBar(onClose        : () -> Unit = {},
                             onNotSelected  : () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(text = "Select a restaurant",
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            IconButton(onClose) {
                Icon(imageVector = Icons.Default.Clear,
                     contentDescription = null)
            }
        },
        actions = {
            TextButton(onClick = { onNotSelected() }) {
                Text(text = "Not Selected")
            }
        }
    )
}

@Composable
fun SearchBar(
    keyword: String,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        Modifier.padding(start = 8.dp, end = 8.dp),
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = keyword,
            onValueChange = {
                onValueChange(it)
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
                        if (keyword.isEmpty())
                            Text(
                                text = "Write a restaurant",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        Modifier
                            .size(20.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onDelete.invoke()
                            }
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
            override suspend fun invoke(keyword: String): List<SelectRestaurantData> {
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