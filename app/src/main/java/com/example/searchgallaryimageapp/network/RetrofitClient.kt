package com.example.searchgallaryimageapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private lateinit var retrofitClient: Retrofit
    private val gson = GsonBuilder().create()
    var BASE_URL: String = "https://api.imgur.com/3/gallery/search/"

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun <T> createIndiaService(serviceClass: Class<T>?): T {
        if (!::retrofitClient.isInitialized) {
            retrofitClient = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofitClient.create(serviceClass)
    }
}