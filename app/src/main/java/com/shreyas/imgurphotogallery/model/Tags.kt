package com.shreyas.imgurphotogallery.model

import java.io.Serializable

data class Tags(
    val name: String,
    val display_name: String,
    val followers: Long,
    val total_items: Long,
    val following: Boolean,
    val is_whitelisted: Boolean,
    val background_hash: String,
    val thumbnail_hash: String?,
    val accent: String,
    val background_is_animated: Boolean,
    val thumbnail_is_animated: Boolean,
    val is_promoted: Boolean,
    val description: String,
    val logo_hash: String,
    val logo_destination_url: String?,
    val description_annotations: DescriptionAnnotations
) : Serializable {

}
