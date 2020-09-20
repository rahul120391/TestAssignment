package com.cardinalHealth.test.usecase

import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.dataSources.IAlbumPhoto
import com.cardinalHealth.test.network.baseusecase.UseCase
import com.cardinalHealth.test.repository.AlbumPhotoRepository
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@ActivityScoped
class GetAllPhotos @Inject constructor(private val albumPhotoRepository: AlbumPhotoRepository)
    :UseCase<GetAllPhotos.RequestValues,GetAllPhotos.ResponseValues>(){

    private lateinit var disposable: Disposable
    override fun executeUseCase(requestValues: RequestValues?){
        disposable=albumPhotoRepository.getPhotos(requestValues?.albumId?:-1, object : IAlbumPhoto.IPhoto {

            override fun onSuccess(list: ArrayList<Photo>) {
                useCaseCallback?.onSuccess(ResponseValues(list))
            }

            override fun onError(code:Int) {
                useCaseCallback?.onError(code)
            }
        })
    }
    fun provideDisposable() = disposable
    class RequestValues(val albumId:Int):UseCase.RequestValues
    class ResponseValues(val list:ArrayList<Photo>):ResponseValue
}