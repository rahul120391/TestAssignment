package com.cardinalHealth.test.network.retrofit

import com.cardinalHealth.test.network.url.RequestUrl.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){
    companion object{
        fun createRetrofitService(): RetrofitServiceAnnotator {
            val interceptor: HttpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
            val retrofitClient: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
            return retrofitClient.create(
                RetrofitServiceAnnotator::
                class.java
            )
        }
    }
}