package com.sryang.addreview.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.addreview.usecase.SelectRestaurantUseCase
import com.sryang.addreview.uistate.SelectRestaurantUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRestaurantViewModel @Inject constructor(
    private val selectRestaurantService: SelectRestaurantUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectRestaurantUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _uiState.emit(
                    uiState.value.copy(
                        isLoading = false
                    )
                )
                val result = selectRestaurantService.getRestaurant()

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
}