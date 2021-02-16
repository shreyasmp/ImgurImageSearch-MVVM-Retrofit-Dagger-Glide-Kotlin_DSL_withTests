package com.shreyas.imgurphotogallery.model

import java.io.Serializable

data class ImgurImageDetails(
    val id: String,
    val title: String,
    val description: String?,
    val datetime: String,
    val cover: String,
    val cover_width: Int,
    val cover_height: Int,
    val account_url: String,
    val account_id: Long,
    val privacy: String,
    val layout: String,
    val views: Long,
    val link: String,
    val ups: Long,
    val downs: Long,
    val points: Int,
    val score: Int,
    val is_album: Boolean,
    val vote: String?,
    val favorite: Boolean,
    val nsfw: Boolean?,
    val section: String,
    val comment_count: Long,
    val favorite_count: Long,
    val topic: String,
    val topic_id: Long,
    val images_count: Long,
    val in_gallery: Boolean,
    val is_ad: Boolean,
    val tags: List<Tags>,
    val ad_type: Int,
    val ad_url: String,
    val in_most_viral: Boolean,
    val include_album_ads: Boolean,
    val images: List<Images>?,
    val ad_config: AdConfig,
) : Serializable {

}
