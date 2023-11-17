package com.sryang.addreview.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoginUseCase {
    val isLogin: Flow<Boolean>
}