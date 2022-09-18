package com.example.searchgallaryimageapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.searchgallaryimageapp.R
import kotlinx.android.synthetic.main.fragment_search_image_dialog.*

class ImageDialog : DialogFragment() {

    companion object {

        const val TAG = "ImageDialog"
        private const val KEY_ID = "KEY_ID"
        private const val KEY_IMAGE = "KEY_IMAGE"

        fun newInstance(imageId: String, imageLink: String): ImageDialog {

            return ImageDialog().apply {
                this.arguments = Bundle().apply {
                    this.putString(KEY_ID, imageId)
                    this.putString(KEY_IMAGE, imageLink)
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_image_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
       // view.tvTitle.text = arguments?.getString(KEY_ID)
        //view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)

        Log.d("imageSearch11","${arguments?.getString(KEY_ID)}")
        var searchImg = arguments?.getString(KEY_IMAGE)

        Glide.with(view)
            .load(searchImg)
            .into(imageSearch)
    }

    private fun setupClickListeners(view: View) {
       /* view.btnPositive.setOnClickListener {
            dismiss()
        }
        view.btnNegative.setOnClickListener {
            dismiss()
        }*/
    }

}
