package com.sarang.torang.addreview.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.uistate.AddReviewUiState
import com.sarang.torang.addreview.uistate.Picture
import com.sarang.torang.addreview.usecase.AddReviewUseCase
import com.sarang.torang.addreview.usecase.GetReviewUseCase
import com.sarang.torang.addreview.usecase.IsLoginUseCase
import com.sarang.torang.addreview.usecase.ModReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val tag : String = "__AddReviewViewModel"
@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val modReviewUseCase: ModReviewUseCase,
    private val isLoginUseCase: IsLoginUseCase,
    private val getReviewUseCase: GetReviewUseCase
) : ViewModel() {

    var uiState : AddReviewUiState by mutableStateOf(AddReviewUiState())
        private set
    val isLogin = isLoginUseCase.isLogin

    fun onShare(onShared: () -> Unit,
                context: Context) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isProgress = true)
                addReviewUseCase.invoke(
                    contents        = uiState.contents,
                    rating          = uiState.rating,
                    files           = uiState.list!!.map { it.url },
                    restaurantId    = uiState.selectedRestaurant?.restaurantId,
                    context         = context
                )
                onShared.invoke()
            } catch (e: Exception) {
                uiState = uiState.copy(errorMsg = e.message)
            } finally {
                uiState = uiState.copy(isProgress = false)
            }
        }
    }

    fun onModify(onShared: () -> Unit,
                 context: Context) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isProgress = true)
                modReviewUseCase.invoke(
                    reviewId = uiState.reviewId!!,
                    contents = uiState.contents,
                    rating = uiState.rating,
                    files = uiState.list,
                    restaurantId = uiState.selectedRestaurant?.restaurantId ?: 0,
                    uploadedImage = uiState.list?.map { it.pictureId },
                    context = context
                )
                onShared.invoke()
            } catch (e: Exception) {
                uiState = uiState.copy(errorMsg = e.message)
            } finally {
                uiState = uiState.copy(isProgress = false)
            }
        }
    }

    fun selectPictures(list: List<String>) {
        viewModelScope.launch {
            uiState = uiState.copy(list = list.map { Picture(url = it) })
        }
    }

    fun selectPicturesInMod(list: List<String>) {
        viewModelScope.launch {
            uiState = uiState.copy(
                    list = ArrayList(uiState.list).apply {
                        addAll(list.map { Picture(url = it) })
                    }
                )
        }
    }

    fun inputText(contents: String) {
        uiState = uiState.copy(contents = contents)
    }

    fun selectRestaurant(selectedRestaurant: SelectRestaurantData) {
        uiState = uiState.copy(selectedRestaurant = selectedRestaurant)
    }

    fun deleteRestaurantAndContents() {
        viewModelScope.launch {
            uiState = uiState.copy(selectedRestaurant = null, contents = "")
        }
    }

    fun load(reviewId: Int) {
        uiState = uiState.copy(retry = false, isLoading = true)
        viewModelScope.launch {
            try {
                uiState = getReviewUseCase.invoke(reviewId)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
                uiState = uiState.copy(retry = true, isLoading = false)
            }
        }
    }

    fun onDeletePicture(url: String) {
        uiState =
            uiState.copy(list = uiState.list?.filter {
                it.url != url
            })
    }

    fun notSelectRestaurant() {
        uiState =  uiState.copy(selectedRestaurant = null)
    }

    fun changeRating(rating: Float) {
        Log.d(tag, "changeRating:${rating}")
        uiState = uiState.copy(rating = rating)
    }
}