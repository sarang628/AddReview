package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.Picture

val ImageSize = 100.dp

@Composable
fun WriteReview(
    uiState: AddReviewUiState,      // 리뷰 추가 uiState
    onShare: () -> Unit,            // 공유 클릭
    onBack: () -> Unit,        // 뒤로가기 클릭
    onRestaurant: () -> Unit,       // 음식점 추가 클릭
    isShareAble: Boolean,           // 공유 가능 여부
    onTextChange: (String) -> Unit,  // 내용 입력 시
    isModify: Boolean = false,
    onDeletePicture: (String) -> Unit,
    onAddPicture: (() -> Unit)? = null
) {
    Column(
        Modifier
            .fillMaxHeight()
    ) {
        // titlebar
        ReviewTitleBar(
            title = if (isModify) "Modify" else "New Post",
            onShare = onShare,
            onBack = onBack,
            isShareAble = isShareAble,
            sendTitle = if (isModify) "update" else "share",
        )
        // selected picture
        uiState.list?.let {
            SelectedPicture(
                list = it.map { it.url },
                onDelete = onDeletePicture,
                onAddPicture = onAddPicture
            )
        }
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
        WriteCaption(input = uiState.contents, onValueChange = onTextChange)
        Spacer(modifier = Modifier.height(5.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Text(text = uiState.errorMsg ?: "")
    }
}

@Composable
fun ModifyReview(
    uiState: AddReviewUiState,
    onShare: () -> Unit,            // 공유 클릭
    onBack: () -> Unit,        // 뒤로가기 클릭
    onRestaurant: () -> Unit,       // 음식점 추가 클릭
    isShareAble: Boolean,           // 공유 가능 여부
    onTextChange: (String) -> Unit,  // 내용 입력 시
    onDeletePicture: (String) -> Unit
) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        return
    }

    WriteReview(
        uiState = uiState,
        onShare = onShare,
        onBack = onBack,
        onRestaurant = onRestaurant,
        isShareAble = isShareAble,
        onTextChange = onTextChange,
        isModify = true,
        onDeletePicture = onDeletePicture
    )
}

@Preview
@Composable
fun PreviewAddReview() {
    WriteReview(
        uiState = AddReviewUiState(list = ArrayList<Picture>().apply {
            add(Picture(url = "1"))
        }),
        onShare = { /*TODO*/ },
        onBack = {},
        onRestaurant = { /*TODO*/ },
        isShareAble = false,
        onTextChange = {},
        isModify = true,
        onDeletePicture = {}
    )
}