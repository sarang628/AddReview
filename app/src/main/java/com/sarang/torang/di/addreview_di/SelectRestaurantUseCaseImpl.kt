package com.sarang.torang.di.addreview_di

import com.sarang.torang.addreview.data.SelectRestaurantData
import com.sarang.torang.addreview.usecase.SelectRestaurantUseCase
import com.sarang.torang.api.ApiRestaurant
import com.sarang.torang.data.Filter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SelectRestaurantUseCaseImpl {
    @Provides
    fun provideSelectRestaurantService(apiRestaurant: ApiRestaurant): SelectRestaurantUseCase {
        return object : SelectRestaurantUseCase {
            override suspend fun invoke(keyword: String): List<SelectRestaurantData> {
                val result = apiRestaurant.getFilterRestaurant(Filter(keyword = keyword))
                return result.map { it.toSelectRestaurantData() }
            }
        }
    }
}
