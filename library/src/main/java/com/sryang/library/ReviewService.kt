package com.sryang.library

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReviewService {
    suspend fun addReview(
        params: HashMap<String, RequestBody>,
        file: ArrayList<MultipartBody.Part>
    ): String
}