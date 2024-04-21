package com.sarang.torang.addreview.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoginUseCase {
    val isLogin: Flow<Boolean>
}