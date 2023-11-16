package com.sryang.addreview.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.addreview.data.SelectRestaurantData
import com.sryang.addreview.uistate.AddReviewUiState
import com.sryang.addreview.usecase.ReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val addReviewService: ReviewUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddReviewUiState> = MutableStateFlow(AddReviewUiState())

    val uiState = _uiState.asStateFlow()

    fun onShare(onShared: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.emit(uiState.value.copy(isProgress = true))
                addReviewService.addReview(
                    params = HashMap<String, RequestBody>().apply {
                        put(
                            "user_id",
                            1.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "contents",
                            uiState.value.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "torang_id",
                            uiState.value.selectedRestaurant?.restaurantId.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "rating",
                            3.0f.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                    },
                    file = if (uiState.value.list != null) filesToMultipart(uiState.value.list!!) else ArrayList()
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

fun filesToMultipart(file: List<String>): ArrayList<MultipartBody.Part> {
    val list = ArrayList<MultipartBody.Part>()
        .apply {
            addAll(
                file.stream().map {
                    val file = File(it)
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
                }.toList()
            )
        }
    return list
}