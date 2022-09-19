package com.example.searchgallaryimageapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchgallaryimageapp.R
import com.example.searchgallaryimageapp.modal.AddImageModal

class SearchImageAdapter(
    private
    var imageList: List<AddImageModal>, private val context: Context,
    private val onItemClickListener: (position: AddImageModal) -> Unit
) : RecyclerView.Adapter<SearchImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.images_row, parent, false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = imageList.get(position)

        Glide.with(context)
            .load(dataModel?.link)
            .into(holder.ivImage)

        holder.itemView.setOnClickListener { view ->
            onItemClickListener(imageList[position])
        }

    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        var titleTextView: TextView
        var title: TextView
        var ivImage: ImageView

        init {
            titleTextView = itemLayoutView.findViewById(R.id.image_title)
            ivImage = itemLayoutView.findViewById(R.id.imagephoto)
            title = itemLayoutView.findViewById(R.id.id_image)
        }
    }

    fun updateList(imgList: List<AddImageModal>) {
        imageList = imgList
        notifyDataSetChanged()
    }
}