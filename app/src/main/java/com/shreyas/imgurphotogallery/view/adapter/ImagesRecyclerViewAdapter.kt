package com.shreyas.imgurphotogallery.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shreyas.imgurphotogallery.R
import com.shreyas.imgurphotogallery.databinding.ImageListItemBinding
import com.shreyas.imgurphotogallery.model.Images
import com.shreyas.imgurphotogallery.view.callback.ImageListItemClickListener

class ImagesRecyclerViewAdapter() :
    RecyclerView.Adapter<ImagesRecyclerViewAdapter.ImageViewHolder>() {
    
    @VisibleForTesting
    internal lateinit var imageList: List<Images>

    private lateinit var imageListItemClickListener: ImageListItemClickListener

    constructor(
        listOfImages: MutableList<Images>,
        clickListener: ImageListItemClickListener
    ) : this() {
        imageList = listOfImages
        imageListItemClickListener = clickListener
    }

    inner class ImageViewHolder(val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Images) {
            binding.image = image
            // Using glide api for loading and caching image faster for recyclerview
            Glide.with(binding.root).load(image.link).into(binding.searchedImage)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ImageListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.image_list_item, parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
        holder.binding.imageListCard.setOnClickListener {
            imageListItemClickListener.onClick(imageList[position])
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = imageList.size

    fun setImgurImages(listOfImages: List<Images>) {
        imageList = listOfImages
        notifyDataSetChanged()
    }
}