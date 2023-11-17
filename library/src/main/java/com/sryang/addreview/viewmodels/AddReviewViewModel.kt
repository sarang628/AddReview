package com.sryang.addreview.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.uistate.AddReviewUiState
import com.sryang.addreview.usecase.AddReviewUseCase
import com.sryang.addreview.usecase.IsLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewService: AddReviewUseCase,
    private val isLoginUseCase: IsLoginUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddReviewUiState> = MutableStateFlow(AddReviewUiState())
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginUseCase.isLogin

    fun onShare(onShared: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.emit(uiState.value.copy(isProgress = true))
                addReviewService.addReview(
                    contents = uiState.value.contents,
                    rating = uiState.value.rating,
                    files = uiState.value.list!!,
                    restaurantId = uiState.value.selectedRestaurant?.restaurantId ?: 0
                )
                _uiState.emit(uiState.value.copy(isProgress = false))
                onShared.invoke()
            } catch (e: Exception) {
                Log.d("AddReviewViewModel", e.toString())
                _uiState.emit(
                    uiState.value.copy(
                        isProgress = false,
                        errorMsg = e.toString()
                    )
                )
            }
        }
    }

    fun selectPictures(list: List<String>) {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    list = ArrayList(list)
                )
            )
        }
    }

    fun inputText(it: String) {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    contents = it
                )
            )
        }
    }

    fun selectRestaurant(it: SelectRestaurantData) {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    selectedRestaurant = it
                )
            )
        }
    }

    fun deleteRestaurantAndContents() {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    selectedRestaurant = null,
                    contents = ""
                )
            )
        }
    }
}