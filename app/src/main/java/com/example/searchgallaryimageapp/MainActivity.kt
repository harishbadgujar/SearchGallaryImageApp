package com.example.searchgallaryimageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchgallaryimageapp.adapter.SearchImageAdapter
import com.example.searchgallaryimageapp.modal.AddImageModal
import com.example.searchgallaryimageapp.ui.ImageDialog
import com.example.searchgallaryimageapp.viewmodal.SearchImageViewModal
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var searchImageViewmodel: SearchImageViewModal
    private lateinit var searchImageAdapter: SearchImageAdapter
    val list: ArrayList<AddImageModal> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupObservers()
        setSearchItems()
        onClickIvClose()
        setupView()
    }

    private fun setupView() {
        searchImageAdapter = SearchImageAdapter(list, MainActivity@ this) {
            ImageDialog.newInstance(it.id, it.link).show(supportFragmentManager, ImageDialog.TAG)
        }
        recyclerView.adapter = searchImageAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun setSearchItems() {

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    ivClose.visibility = VISIBLE
                } else {
                    ivClose.visibility = INVISIBLE
                }
                searchImageViewmodel.serchFilter(s.toString()) {
                    filterItems(it)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun filterItems(imgName: String) {
        val list: ArrayList<AddImageModal> = ArrayList()
        progressBar.isVisible = true
        searchImageViewmodel.searchImgLiveDataList.observe(this, androidx.lifecycle.Observer {

            it?.forEach {
                it.images?.forEach {
                    var i = AddImageModal(it.link, it.id)
                    list.add(i)
                }
            }
            setUpdateImages(list)
        })
        searchImageViewmodel.searchImages(imgName)
    }

    private fun setupObservers() {
        progressBar.isVisible = true
        searchImageViewmodel.photoLiveDataList.observe(this, androidx.lifecycle.Observer {

            it?.forEach {
                it.images?.forEach {
                    var i = AddImageModal(it.link, it.id)
                    list.add(i)
                }
            }

            setUpdateImages(list)
        })
        searchImageViewmodel.getAllImages()
    }

    private fun setupViewModel() {
        searchImageViewmodel = ViewModelProvider(this).get(SearchImageViewModal::class.java)
    }

    private fun setUpdateImages(imageList: List<AddImageModal>) {
        progressBar.isVisible = false
        searchImageAdapter.updateList(imageList)
    }

    private fun onClickIvClose() {
        ivClose.setOnClickListener {
            etSearch.text?.clear()
        }
    }
}


