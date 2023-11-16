package com.sryang.addreview.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sryang.addreview.uistate.AddReviewUiState
import kotlinx.coroutines.launch

@Composable
fun AddReview(
    uiState: AddReviewUiState,      // 리뷰 추가 uiState
    onShare: () -> Unit,            // 공유 클릭
    onBack: (Void?) -> Unit,        // 뒤로가기 클릭
    onRestaurant: () -> Unit,       // 음식점 추가 클릭
    isShareAble: Boolean,           // 공유 가능 여부
    content: String,                // 내용
    onTextChange: (String) -> Unit  // 내용 입력 시
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxHeight()
    ) {
        // titlebar
        ReviewTitleBar(onShare = {
            coroutine.launch {
                onShare.invoke()
            }
        }, onBack = onBack, isShareAble = isShareAble)
        // selected picture
        uiState.list?.let { SelectedPicture(list = it) }
        Spacer(modifier = Modifier.height(5.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        // select restaurant
        SelectRestaurantLabel(
            uiState.selectedRestaurant?.restaurantName,
            onRestaurant = onRestaurant
        )
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        // Write a caption
        WriteCaption(input = content, onValueChange = onTextChange)
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Text(text = uiState.errorMsg ?: "")
    }
}

val ImageSize = 100.dp

@Preview
@Composable
fun PreviewAddReview() {
    AddReview(
        uiState = AddReviewUiState(list = ArrayList<String>().apply {
            add("1")
        }),
        onShare = { /*TODO*/ },
        onBack = {},
        onRestaurant = { /*TODO*/ },
        isShareAble = false,
        content = "",
        onTextChange = {}
    )
}