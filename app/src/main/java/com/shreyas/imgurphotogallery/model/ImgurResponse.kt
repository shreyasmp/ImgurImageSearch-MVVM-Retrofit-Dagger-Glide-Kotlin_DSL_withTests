package com.shreyas.imgurphotogallery.model

import java.io.Serializable

data class ImgurResponse(
    val data: List<ImgurImageDetails>,
    val success: Boolean,
    val status: Int
) : Serializable {

}
