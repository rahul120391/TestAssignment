package com.cardinalHealth.test.dataSources

import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.database.MyDatabase
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class LocalDataSource @Inject constructor(private val myDatabase: MyDatabase):IAlbumPhoto {

    override fun getAlbums(userId: Int, callBack: IAlbumPhoto.IAlbum): Disposable =
        Single.just(myDatabase.getAlbumPhotoDao().getAllAlbums())
            .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io()).subscribe {
                result->
                result?.let {
                    callBack.onSuccess(it.toCollection(ArrayList()))
                }?: kotlin.run {
                    callBack.onSuccess(ArrayList())
                }
            }


    override fun getPhotos(albumId: Int, callBack: IAlbumPhoto.IPhoto): Disposable =
        Single.just(myDatabase.getAlbumPhotoDao().getAllPhotos(albumId))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe {
                    result->
                result?.let {
                    callBack.onSuccess(it.toCollection(ArrayList()))
                }?: kotlin.run {
                    callBack.onSuccess(ArrayList())
                }
            }

    fun saveAlbumsToDatabase(userId: Int,list: List<Album>,callBack: IAlbumPhoto.IAlbum){
        CoroutineScope(Dispatchers.IO).launch {
            myDatabase.getAlbumPhotoDao().apply {
                insertAlbums(list)
                getAlbums(userId,callBack)
            }
        }

    }

    fun savePhotosToDatabase(albumId: Int,list: ArrayList<Photo>,callBack: IAlbumPhoto.IPhoto){
        myDatabase.getAlbumPhotoDao().apply {
            insertPhotos(list)
            getPhotos(albumId, callBack)
        }
    }
}