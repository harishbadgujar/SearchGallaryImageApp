package com.example.searchgallaryimageapp.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchgallaryimageapp.listner.SearchImageListner
import com.example.searchgallaryimageapp.modal.Data
import com.example.searchgallaryimageapp.modal.ImageData
import com.example.searchgallaryimageapp.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class SearchImageViewModal : ViewModel() {

    var photoLiveDataList = MutableLiveData<List<Data>>()
    var searchImgLiveDataList = MutableLiveData<List<Data>>()
    val compositeDisposable = CompositeDisposable()

    fun getAllImages() {
        val respImages: (List<Data>?) -> Unit = { s: List<Data>? -> photoLiveDataList.value = s }
        getImages(respImages)
       // SearchImageRepository().imagesRepo(respImages)

    }

    fun searchImages(imgName: String) {
        val searchrespImages: (List<Data>?) -> Unit =
            { searchImg: List<Data>? -> searchImgLiveDataList.value = searchImg }
        getSearchImages(imgName, searchrespImages)
       // SearchImageRepository().searchImageRepository(imgName, searchrespImages)
    }

    fun serchFilter(input: String, result: (String) -> Unit) {
        val r = Observable.create(ObservableOnSubscribe<String> { subscribe ->
            subscribe.onNext(input)
        })
            .map { text -> text.lowercase(Locale.ENGLISH).trim() }
            .debounce(3, TimeUnit.SECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                result.invoke(text)
            }
    }

    fun getImages(action: (List<Data>?) -> Unit) {

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

    fun getSearchImages(imgName: String, searchImgaction: (List<Data>?) -> Unit) {

        val apiRequest = RetrofitClient.createIndiaService(SearchImageListner::class.java)
        val disposable: Disposable = apiRequest.getSearchImages(imgName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ImageData>() {

                override fun onNext(response: ImageData) {
                    val dataList: List<Data> = response.data
                    searchImgaction(dataList)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}