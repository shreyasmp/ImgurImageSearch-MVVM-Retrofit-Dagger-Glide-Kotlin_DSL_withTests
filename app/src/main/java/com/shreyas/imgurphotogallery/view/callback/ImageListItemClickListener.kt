package com.shreyas.imgurphotogallery.view.callback

import com.shreyas.imgurphotogallery.model.Images

interface ImageListItemClickListener {
    fun onClick(image: Images)
}