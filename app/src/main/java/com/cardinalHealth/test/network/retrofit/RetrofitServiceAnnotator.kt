package com.cardinalHealth.test.network.retrofit

import com.cardinalHealth.test.database.DatabaseConstants.ID
import com.cardinalHealth.test.network.url.RequestUrl.ALBUMS
import com.cardinalHealth.test.network.url.RequestUrl.PHOTOS
import io.reactivex.rxjava3.core.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServiceAnnotator {

    @GET(ALBUMS)
    fun getAlbums(@Path(ID) id:Int): Flowable<Response<ResponseBody>>

    @GET(PHOTOS)
    fun getPhotos(@Path(ID) id:Int): Flowable<Response<ResponseBody>>

}