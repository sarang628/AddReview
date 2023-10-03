package com.sryang.torang_repository.di.api

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.sryang.torang_repository.api.ApiRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiRestaurantModule {
    @Singleton
    @Provides
    fun provideApiRestaurant(
        apiRestaurant: ProductApiRestaurant
//        apiRestaurant: LocalApiRestaurant
    ): ApiRestaurant {
        return apiRestaurant.create()
    }
}

@Singleton
class ProductApiRestaurant @Inject constructor(
    private val torangOkHttpClientImpl: TorangOkhttpClient,
    private val retrofitModule: RetrofitModule
) {
    private var url = "http://sarang628.iptime.org:8081/"
    fun create(): ApiRestaurant {
        return retrofitModule
//            .getRetrofit(torangOkHttpClientImpl.getUnsafeOkHttpClient(), url)
            .getRetrofit(torangOkHttpClientImpl.getHttpClient(), url)
            .create(ApiRestaurant::class.java)
    }
}

@Singleton
class LocalApiRestaurant @Inject constructor(
    private val torangOkHttpClientImpl: TorangOkhttpClient,
    private val retrofitModule: RetrofitModule
) {
    private var url = "http://169.254.145.170:8081/"
    fun create(): ApiRestaurant {
        return retrofitModule
//            .getRetrofit(torangOkHttpClientImpl.getUnsafeOkHttpClient(), url)
            .getRetrofit(torangOkHttpClientImpl.getHttpClient(), url)
            .create(ApiRestaurant::class.java)
    }
}

@Composable
fun TestApiRestaurant(apiRestaurant: ApiRestaurant) {
    val coroutine = rememberCoroutineScope()
    Button(onClick = {
        coroutine.launch {
            try {
                apiRestaurant.getRestaurantById(100)
            } catch (e: HttpException) {
                val responseBody = e.response()?.errorBody()
                Log.e("TestApiRestaurant", responseBody?.string() ?: "알 수 없는 오류가 발생했습니다.")

            } catch (e: Exception) {
                Log.e("TestApiRestaurant", e.toString())
            }
        }
    }) {

    }
}