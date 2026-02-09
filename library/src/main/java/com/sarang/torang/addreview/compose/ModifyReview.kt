package com.sarang.torang.addreview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sarang.torang.addreview.uistate.AddReviewUiState

@Composable
fun ModifyReview(uiState         : AddReviewUiState  = AddReviewUiState(),
                 onShare         : () -> Unit        = {},
                 onBack          : () -> Unit        = {},
                 onRestaurant    : () -> Unit        = {},
                 isShareAble     : Boolean           = false,
                 onTextChange    : (String) -> Unit  = {},
                 onDeletePicture : (String) -> Unit  = {},
                 onChangeRating  : (Float) -> Unit   = {} ) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else{
        WriteReview(uiState         = uiState,
                    onShare         = onShare,
                    onBack          = onBack,
                    onRestaurant    = onRestaurant,
                    isShareAble     = isShareAble,
                    onTextChange    = onTextChange,
                    isModify        = true,
                    onDeletePicture = onDeletePicture,
                    onChangeRating  = onChangeRating)
    }
}