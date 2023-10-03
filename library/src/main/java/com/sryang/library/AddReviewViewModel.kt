package com.sryang.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewService: ReviewService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddReviewUiState())

    val uiState = _uiState.asStateFlow()

    fun onShare(addReviewData: AddReviewData) {
        viewModelScope.launch {
            try {
                _uiState.emit(uiState.value.copy(isProgress = true))
                addReviewService.addReview(
                    params = HashMap<String, RequestBody>().apply {
                        put(
                            "contents",
                            addReviewData.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                    },
                    file = filesToMultipart(addReviewData.pictures)
                )
                _uiState.emit(uiState.value.copy(isProgress = false))
            } catch (e: Exception) {
                Log.d("AddReviewViewModel", e.toString())
                _uiState.emit(
                    uiState.value.copy(
                        isProgress = false
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


}