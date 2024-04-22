package com.sarang.torang.addreview.compose

import android.R
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
    onAddPicture: (() -> Unit)? = null,
    onChangeRating: ((Float) -> Unit)? = null,
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
            modifier = Modifier.padding(start = 18.dp),
            uiState.selectedRestaurant?.restaurantName,
            onRestaurant = onRestaurant
        )
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Box(modifier = Modifier.height(50.dp)) {
            AndroidViewRatingBar(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                rating = uiState.rating,
                onChangeRating = onChangeRating
            )
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        // Write a caption
        WriteCaption(input = uiState.contents, onValueChange = onTextChange)
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Text(text = uiState.errorMsg ?: "")
    }
}

@Composable
internal fun AndroidViewRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    isSmall: Boolean = false,
    changable: Boolean = true,
    onChangeRating: ((Float) -> Unit)? = null
) {
    // Adds view to Compose
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // Creates view
            if (isSmall) {
                RatingBar(context, null, R.attr.ratingBarStyleSmall).apply {
                    // Sets up listeners for View -> Compose communication
                    this.rating = rating
                    setIsIndicator(!changable)
                }
            } else {
                RatingBar(context, null, R.attr.ratingBarStyleIndicator).apply {
                    // Sets up listeners for View -> Compose communication
                    this.rating = rating
                    setIsIndicator(!changable)

                }
            }
        },
        update = { view ->
            view.onRatingBarChangeListener = object : OnRatingBarChangeListener {
                override fun onRatingChanged(
                    ratingBar: RatingBar?,
                    rating: Float,
                    fromUser: Boolean
                ) {
                    onChangeRating?.invoke(rating)
                }
            }
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication
        }
    )
}

@Preview
@Composable
fun PreviewWriteReview() {
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