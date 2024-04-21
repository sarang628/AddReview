package com.sarang.torang.di.addreview_di

import com.sarang.torang.addreview.usecase.IsLoginUseCase
import com.sarang.torang.data.dao.LoggedInUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@InstallIn(SingletonComponent::class)
@Module
class IsLoginUseCaseImpl {
    @Provides
    fun provideIsLoginUseCase(
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