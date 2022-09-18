package com.example.searchgallaryimageapp.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchgallaryimageapp.modal.Data
import com.example.searchgallaryimageapp.repository.SearchImageRepository
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit

class SearchImageViewModal : ViewModel() {

    var photoLiveDataList = MutableLiveData<List<Data>>()
    var searchImgLiveDataList = MutableLiveData<List<Data>>()

    fun getAllImages() {

        val respImages: (List<Data>?) -> Unit = { s: List<Data>? -> photoLiveDataList.value = s }
        SearchImageRepository().imagesRepo(respImages)

    }

    fun searchImages(imgName: String) {
        val searchrespImages: (List<Data>?) -> Unit =
            { searchImg: List<Data>? -> searchImgLiveDataList.value = searchImg }
        SearchImageRepository().searchImageRepository(imgName, searchrespImages)
    }

    fun serchFilter(input : String,result : (String) -> Unit){

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
                // filterItems(text)
            }
    }

}