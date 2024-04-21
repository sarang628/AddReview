package com.sarang.torang.addreview.viewmodels

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val modReviewUseCase: ModReviewUseCase,
    private val isLoginUseCase: IsLoginUseCase,
    private val getReviewUseCase: GetReviewUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddReviewUiState> = MutableStateFlow(AddReviewUiState())
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginUseCase.isLogin

    fun onShare(onShared: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isProgress = true) }
                addReviewUseCase.invoke(
                    contents = uiState.value.contents,
                    rating = uiState.value.rating,
                    files = uiState.value.list!!.map { it.url },
                    restaurantId = uiState.value.selectedRestaurant?.restaurantId
                )
                onShared.invoke()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMsg = e.message) }
            } finally {
                _uiState.update { it.copy(isProgress = false) }
            }
        }
    }

    fun onModify(onShared: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isProgress = true) }
                modReviewUseCase.invoke(
                    reviewId = uiState.value.reviewId!!,
                    contents = uiState.value.contents,
                    rating = uiState.value.rating,
                    files = uiState.value.list,
                    restaurantId = uiState.value.selectedRestaurant?.restaurantId ?: 0,
                    uploadedImage = uiState.value.list?.map { it.pictureId }
                )
                onShared.invoke()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMsg = e.message) }
            } finally {
                _uiState.update { it.copy(isProgress = false) }
            }
        }
    }

    fun selectPictures(list: List<String>) {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    list = list.map { Picture(url = it) }
                )
            )
        }
    }

    fun selectPicturesInMod(list: List<String>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    list = ArrayList(it.list).apply {
                        addAll(list.map { Picture(url = it) })
                    }
                )
            }
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

    fun load(reviewId: Int) {
        _uiState.update { it.copy(retry = false, isLoading = true) }
        viewModelScope.launch {
            try {
                _uiState.update {
                    getReviewUseCase.invoke(reviewId)
                }
            } catch (e: Exception) {
                Log.e("_AddReviewViewModel", e.message.toString())
                _uiState.update { it.copy(retry = true, isLoading = false) }
            }
        }
    }

    fun onDeletePicture(url: String) {
        _uiState.update { it ->
            it.copy(list = it.list?.filter {
                it.url != url
            })
        }
    }
}