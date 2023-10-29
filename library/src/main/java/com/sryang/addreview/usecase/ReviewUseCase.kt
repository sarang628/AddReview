package com.sryang.addreview.usecase

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReviewUseCase {
    suspend fun addReview(
        params: HashMap<String, RequestBody>,
        file: ArrayList<MultipartBody.Part>
    ): String
}