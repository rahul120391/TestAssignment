package com.cardinalHealth.test.di

import com.cardinalHealth.test.network.baseusecase.UseCaseHandler
import com.cardinalHealth.test.usecase.GetAllAlbums
import com.cardinalHealth.test.usecase.GetAllPhotos
import com.cardinalHealth.test.utils.Utility
import com.cardinalHealth.test.viewModel.MainActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object  MainActivityModule {

    @Provides
    @ActivityScoped
    fun provideMainActivityViewModel(useCaseHandler: UseCaseHandler,getAllAlbums: GetAllAlbums,getAllPhotos: GetAllPhotos,utility: Utility):MainActivityViewModel= MainActivityViewModel(useCaseHandler, getAllAlbums, getAllPhotos,utility)
}