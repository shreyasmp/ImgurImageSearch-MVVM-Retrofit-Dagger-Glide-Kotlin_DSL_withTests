package com.shreyas.imgurphotogallery.service

import com.shreyas.imgurphotogallery.model.ImgurResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IImgurPhotoService {

    @Headers("Content-Type: application/json", "Authorization: Client-ID 126701cd8332f32")
    @GET(".")
    suspend fun fetchSearchedImageResponse(@Query("q") searchParameters: String): Response<ImgurResponse>
}