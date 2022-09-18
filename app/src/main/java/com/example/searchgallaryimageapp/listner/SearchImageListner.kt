package com.example.searchgallaryimageapp.listner

import com.example.searchgallaryimageapp.modal.ImageData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchImageListner {

    // use url :- https://api.imgur.com/3/gallery/search/?q=window

    @Headers("Authorization:Client-ID 4442d2bb442f675")
    @GET("?q=dogs")
    fun getMyImages(): Observable<ImageData>

    @Headers("Authorization:Client-ID 4442d2bb442f675")
    @GET("?")
    fun getSearchImages(@Query("q") name: String?): Observable<ImageData>
}