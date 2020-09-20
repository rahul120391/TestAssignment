package com.cardinalHealth.test.repository

import com.cardinalHealth.test.constants.Constants.INTERNET_ERROR
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.dataSources.IAlbumPhoto
import com.cardinalHealth.test.dataSources.LocalDataSource
import com.cardinalHealth.test.dataSources.RemoteDataSource
import com.cardinalHealth.test.utils.Utility
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class AlbumPhotoRepository @Inject constructor(private val localDataSource: LocalDataSource,
                                               private val remoteDataSource: RemoteDataSource,private val utility: Utility): IAlbumPhoto{


    override fun getAlbums(userId: Int, callBack: IAlbumPhoto.IAlbum): Disposable = localDataSource.getAlbums(userId,
        object : IAlbumPhoto.IAlbum {
            val reference = this
            override fun onSuccess(list: ArrayList<Album>) {
                 if(list.isNotEmpty()){
                     callBack.onSuccess(list)
                 }
                 else{
                     if(utility.checkNetworkConnectivity()){
                         remoteDataSource.getAlbums(userId, object : IAlbumPhoto.IAlbum {
                             override fun onSuccess(list: ArrayList<Album>) {
                                 localDataSource.saveAlbumsToDatabase(userId,list,reference)
                             }

                             override fun onError(code:Int) {
                                 callBack.onError(code)
                             }
                         })
                     }
                     else{
                         callBack.onError(INTERNET_ERROR)
                     }

                 }
            }

            override fun onError(code:Int) {
                callBack.onError(code)
            }

        })

    override fun getPhotos(albumId: Int, callBack: IAlbumPhoto.IPhoto): Disposable = localDataSource.getPhotos(albumId,
        object : IAlbumPhoto.IPhoto {
            val reference = this
            override fun onSuccess(list: ArrayList<Photo>) {
                if(list.isNotEmpty()){
                    callBack.onSuccess(list)
                }
                else{
                    if(utility.checkNetworkConnectivity()){
                        remoteDataSource.getPhotos(albumId, object : IAlbumPhoto.IPhoto {
                            override fun onSuccess(list: ArrayList<Photo>) {
                                localDataSource.savePhotosToDatabase(albumId,list,reference)
                            }

                            override fun onError(code:Int) {
                                callBack.onError(code)
                            }
                        })
                    }
                    else{
                        callBack.onError(INTERNET_ERROR)
                    }

                }
            }

            override fun onError(code:Int) {
                callBack.onError(code)
            }
        })

}