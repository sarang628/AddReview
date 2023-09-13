package com.sryang.addreview

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.net.toFile
import com.sryang.addreview.di.remote.RemoteReviewModule
import com.sryang.library.SelectPictureAndAddReview
import com.sryang.torang_repository.services.RemoteFeedServices
import com.sryang.torang_repository.services.RemoteReviewService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import kotlin.streams.toList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteReviewService: RemoteReviewService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutine = rememberCoroutineScope()
            SelectPictureAndAddReview(onShare = {
                coroutine.launch {
                    try {
                        remoteReviewService.addReview(
                            params = HashMap<String, RequestBody>().apply {
                                put(
                                    "contents",
                                    it.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                                )
                            },
                            file = filesToMultipart(it.pictures)
                        )
                    } catch (e: Exception) {
                        Log.d("MainActivity", e.toString())
                    }
                }
            }, color = 0xFFFFFFFF)
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
