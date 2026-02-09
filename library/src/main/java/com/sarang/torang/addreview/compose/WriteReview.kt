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
import androidx.compose.material3.Scaffold
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
    uiState         : AddReviewUiState  = AddReviewUiState(),
    onShare         : () -> Unit        = {},
    onBack          : () -> Unit        = {},
    onRestaurant    : () -> Unit        = {},
    isShareAble     : Boolean           = false,
    onTextChange    : (String) -> Unit  = {},
    isModify        : Boolean           = false,
    onDeletePicture : (String) -> Unit  = {},
    onAddPicture    : () -> Unit        = {},
    onChangeRating  : (Float) -> Unit   = {},
) {
    Scaffold(
        topBar = { ReviewTitleBar(title = if (isModify) "Modify" else "New post",
                                  onShare = onShare,
                                  onBack = onBack,
                                  isShareAble = isShareAble,
                                  sendTitle = if (isModify) "update" else "share") }) {
        Column(Modifier.padding(it)) {
            SelectedPicture(
                modifier = Modifier.padding(horizontal = 8.dp),
                list = uiState.list?.map { it.url } ?: listOf(),
                onDelete = onDeletePicture,
                onAddPicture = onAddPicture
            )
            Spacer(modifier = Modifier.height(5.dp))

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            SelectRestaurantLabel(
                modifier = Modifier.padding(start = 10.dp),
                selectedRestaurantName = uiState.selectedRestaurant?.restaurantName ?: "",
                onRestaurant = onRestaurant
            )
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Box(modifier = Modifier.height(50.dp)) {
                AndroidViewRatingBar(
                    modifier = Modifier.padding(start = 16.dp)
                                       .align(Alignment.CenterStart),
                    rating = uiState.rating,
                    onChangeRating = onChangeRating
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            WriteCaption(input = uiState.contents, onValueChange = onTextChange)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Text(text = uiState.errorMsg ?: "")
        }
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
    WriteReview(//Preview
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