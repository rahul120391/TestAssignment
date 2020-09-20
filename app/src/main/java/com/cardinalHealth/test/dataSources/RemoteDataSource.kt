package com.cardinalHealth.test.dataSources

import com.cardinalHealth.test.constants.Constants.ERROR_WHILE_FETCHING
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.network.retrofit.RetrofitServiceAnnotator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONTokener
import javax.inject.Inject
import javax.inject.Singleton


@ActivityScoped
class RemoteDataSource @Inject constructor(private val retrofitServiceAnnotator: RetrofitServiceAnnotator):IAlbumPhoto {


    override fun getAlbums(userId: Int, callBack: IAlbumPhoto.IAlbum): Disposable =
        retrofitServiceAnnotator.getAlbums(userId).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
            .subscribe({
                response->
               response.body()?.string()?.let {
                       val value=JSONTokener(it).nextValue()
                       if(value is JSONArray){
                           val gSon = Gson()
                           val listType = object : TypeToken<Array<Album>>() {}.type
                           val responseList = ArrayList<Album>()
                           val res: Array<Album> = gSon.fromJson(it, listType)
                           responseList.addAll(res)
                           callBack.onSuccess(responseList)
                       }
                       else{
                           callBack.onError(ERROR_WHILE_FETCHING)
                       }
               }?: kotlin.run {
                   callBack.onError(ERROR_WHILE_FETCHING)
               }
            },{
                callBack.onError(ERROR_WHILE_FETCHING)
            })

    override fun getPhotos(albumId: Int, callBack: IAlbumPhoto.IPhoto): Disposable =
        retrofitServiceAnnotator.getPhotos(albumId).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
       .subscribe({
           response->
        response.body()?.string()?.let {
            val value=JSONTokener(it).nextValue()
            if(value is JSONArray){
                val gSon = Gson()
                val listType = object : TypeToken<Array<Photo>>() {}.type
                val responseList = ArrayList<Photo>()
                val res: Array<Photo> = gSon.fromJson(it, listType)
                responseList.addAll(res)
                callBack.onSuccess(responseList)
            }
            else{
                callBack.onError(ERROR_WHILE_FETCHING)
            }
        }?: kotlin.run {
            callBack.onError(ERROR_WHILE_FETCHING)
        }
    },{
        callBack.onError(ERROR_WHILE_FETCHING)
    })
}