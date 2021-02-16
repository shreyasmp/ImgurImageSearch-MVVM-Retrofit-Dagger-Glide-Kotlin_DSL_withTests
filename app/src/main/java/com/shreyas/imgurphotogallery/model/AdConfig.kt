package com.shreyas.imgurphotogallery.model

import java.io.Serializable

data class AdConfig(
    val safeFlags: List<String>?,
    val highRiskFlags: List<String>?,
    val unsafeFlags: List<String>?,
    val wallUnsafeFlags: List<String>?,
    val showsAds: Boolean,
) : Serializable {

}
