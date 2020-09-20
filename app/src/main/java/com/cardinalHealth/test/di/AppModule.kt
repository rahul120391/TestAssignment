package com.cardinalHealth.test.di

import android.content.Context
import androidx.room.Room
import com.cardinalHealth.test.application.MyApplication
import com.cardinalHealth.test.database.DatabaseConstants.DATABASE_NAME
import com.cardinalHealth.test.database.MyDatabase
import com.cardinalHealth.test.network.baseusecase.UseCaseHandler
import com.cardinalHealth.test.network.retrofit.RetrofitClient
import com.cardinalHealth.test.network.retrofit.RetrofitServiceAnnotator
import com.cardinalHealth.test.utils.Utility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    internal fun provideRetrofitServiceAnnotator(): RetrofitServiceAnnotator =
        RetrofitClient.createRetrofitService()

    @Provides
    @Singleton
    internal fun provideUtils(@ApplicationContext context:Context): Utility = Utility.getInstance(context)
    @Provides
    @Singleton
    internal fun provideUseCaseHandlerInstance() = UseCaseHandler.getInstance()

    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext context:Context): MyDatabase =
        Room.databaseBuilder(context,MyDatabase::class.java,DATABASE_NAME)
        .build()


}