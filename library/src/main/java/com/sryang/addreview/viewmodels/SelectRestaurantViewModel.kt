package com.sryang.addreview.viewmodels

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.addreview.usecase.SelectRestaurantUseCase
import com.sryang.addreview.uistate.SelectRestaurantUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRestaurantViewModel @Inject constructor(
    private val selectRestaurantService: SelectRestaurantUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectRestaurantUiState())

    val uiState = _uiState.asStateFlow()

    var job: Job? = null

    fun onValueChange(keyword: String) {
        _uiState.update { it.copy(keyword = keyword) }
        job?.cancel()
        if (keyword.length > 1) {
            job = viewModelScope.launch {
                delay(1000)
                search(keyword)
            }
        }
    }

    private fun search(keyword: String) {
        viewModelScope.launch {
            try {
                _uiState.emit(
                    uiState.value.copy(
                        isLoading = false
                    )
                )
                val result = selectRestaurantService.invoke(keyword)

                _uiState.emit(
                    uiState.value.copy(
                        isLoading = false,
                        restaurantList = result
                    )
                )
            } catch (e: Exception) {
                Log.e("SelectRestaurantViewModel", e.toString())
            }
        }
    }

    fun clearKeyword() {
        Log.d("_SelectRestaurantViewModel", "clearKeyword")
        _uiState.update { it.copy(keyword = "") }
    }
}