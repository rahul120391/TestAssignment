package com.cardinalHealth.test.dataSources

import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import io.reactivex.rxjava3.disposables.Disposable

interface IAlbumPhoto {

    interface IAlbum{
        fun onSuccess(list:ArrayList<Album>)
        fun onError(code:Int)
    }

    interface IPhoto{
        fun onSuccess(list:ArrayList<Photo>)
        fun onError(code:Int)
    }

    fun getAlbums(userId:Int,callBack:IAlbum):Disposable
    fun getPhotos(albumId:Int,callBack: IPhoto):Disposable
}