package com.cardinalHealth.test.usecase

import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataSources.IAlbumPhoto
import com.cardinalHealth.test.network.baseusecase.UseCase
import com.cardinalHealth.test.repository.AlbumPhotoRepository
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class GetAllAlbums @Inject constructor(private val albumPhotoRepository: AlbumPhotoRepository):
    UseCase<GetAllAlbums.RequestValues,GetAllAlbums.ResponseValues>(){

    private lateinit var disposable: Disposable
    override fun executeUseCase(requestValues: RequestValues?){
        disposable = albumPhotoRepository.getAlbums(requestValues?.userId?:-1, object : IAlbumPhoto.IAlbum {
            override fun onSuccess(list: ArrayList<Album>) {
                useCaseCallback?.onSuccess(ResponseValues(list))
            }

            override fun onError(code:Int) {
                useCaseCallback?.onError(code)
            }
        })
    }

    fun getDisposable():Disposable = disposable
      class RequestValues(val userId:Int): UseCase.RequestValues
      class ResponseValues(val list:ArrayList<Album>): UseCase.ResponseValue
}