package com.sryang.addreview.di.addreview

import com.sryang.addreview.usecase.AddReviewUseCase
import com.sryang.addreview.usecase.IsLoginUseCase
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@InstallIn(SingletonComponent::class)
@Module
class AddReviewModule {
    @Provides
    fun provideReviewService(
        apiReview: ApiReview,
        loggedInUserDao: LoggedInUserDao
    ): AddReviewUseCase {
        return object : AddReviewUseCase {
            override suspend fun addReview(
                contents: String,
                restaurantId: Int,
                rating: Float,
                files: List<String>
            ) {
                val user = loggedInUserDao.getLoggedInUser1()

                if (user == null)
                    return

                apiReview.addReview(
                    params = HashMap<String, RequestBody>().apply {
                        put(
                            "user_id",
                            user.userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "contents",
                            contents.toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "torang_id",
                            restaurantId.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        put(
                            "rating",
                            rating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                    },
                    file = filesToMultipart(files)
                )
            }

        }
    }

    @Provides
    fun ProvideIsLoginUsecase(
        loggedInUserDao: LoggedInUserDao
    ): IsLoginUseCase {
        return object : IsLoginUseCase {
            override val isLogin: Flow<Boolean>
                get() = loggedInUserDao.getLoggedInUser().map {
                    it != null
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