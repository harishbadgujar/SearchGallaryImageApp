package com.example.searchgallaryimageapp.repository

import android.util.Log
import com.example.searchgallaryimageapp.listner.SearchImageListner
import com.example.searchgallaryimageapp.modal.Data
import com.example.searchgallaryimageapp.modal.ImageData
import com.example.searchgallaryimageapp.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchImageRepository {

    fun imagesRepo(action: (List<Data>?) -> Unit){
        val compositeDisposable = CompositeDisposable()
        val apiRequest = RetrofitClient.createIndiaService(SearchImageListner::class.java)
        val disposable: Disposable = apiRequest.getMyImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageData>() {

                override fun onNext(response: ImageData) {
                    val dataList: List<Data> = response.data
                    action(dataList)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

        compositeDisposable.add(disposable)
    }

    fun searchImageRepository(imgName: String,searchImgaction: (List<Data>?) -> Unit) {

        val compositeDisposable = CompositeDisposable()
        val apiRequest = RetrofitClient.createIndiaService(SearchImageListner::class.java)
        val disposable: Disposable = apiRequest.getSearchImages(imgName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageData>() {

                override fun onNext(response: ImageData) {
                    val dataList: List<Data> = response.data
                  //  Log.d("searchImages102","${dataList.size.toString()}")
                    searchImgaction(dataList)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

        compositeDisposable.add(disposable)
    }

}