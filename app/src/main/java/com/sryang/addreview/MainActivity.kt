package com.sryang.addreview

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.net.toFile
import com.sryang.addreview.di.remote.RemoteReviewModule
import com.sryang.library.SelectPictureAndAddReview
import com.sryang.torang_repository.services.RemoteFeedServices
import com.sryang.torang_repository.services.RemoteReviewService
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.streams.toList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteReviewService: RemoteReviewService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isProgress by remember { mutableStateOf(false) }
            val coroutine = rememberCoroutineScope()
            Box {
                SelectPictureAndAddReview(onShare = {
                    coroutine.launch {
                        try {
                            isProgress = true
                            remoteReviewService.addReview(
                                params = HashMap<String, RequestBody>().apply {
                                    put(
                                        "contents",
                                        it.contents.toRequestBody("text/plain".toMediaTypeOrNull())
                                    )
                                },
                                file = filesToMultipart(it.pictures)
                            )
                            isProgress = false
                        } catch (e: Exception) {
                            Log.d("MainActivity", e.toString())
                            isProgress = false
                        }
                    }
                }, color = 0xFFFFFFFF)

                if (isProgress)
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

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
