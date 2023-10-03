package com.sryang.library

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

data class AddReviewUiState(
    val isProgress: Boolean = false,
    val list: List<String>? = null
)
