package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.Picture

@Composable
fun ModifyReview(
    uiState: AddReviewUiState,
    onShare: () -> Unit,            // 공유 클릭
    onBack: () -> Unit,        // 뒤로가기 클릭
    onRestaurant: () -> Unit,       // 음식점 추가 클릭
    isShareAble: Boolean,           // 공유 가능 여부
    onTextChange: (String) -> Unit,  // 내용 입력 시
    onDeletePicture: (String) -> Unit,
    onChangeRating: ((Float) -> Unit)? = null,
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
        onDeletePicture = onDeletePicture,
        onChangeRating = onChangeRating
    )
}