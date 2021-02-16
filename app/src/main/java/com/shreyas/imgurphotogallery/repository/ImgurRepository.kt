package com.shreyas.imgurphotogallery.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shreyas.imgurphotogallery.model.ImgurResponse
import com.shreyas.imgurphotogallery.service.IImgurPhotoService
import com.shreyas.imgurphotogallery.utils.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface IImgurRepository {
    suspend fun getImgurImageListAsSearched(searchedImage: String): ResultWrapper<LiveData<ImgurResponse>>
}

@Singleton
open class ImgurRepository @Inject constructor(
    private val service: IImgurPhotoService
) : IImgurRepository {

    companion object {
        private val TAG = ImgurRepository::class.java.simpleName
    }

    override suspend fun getImgurImageListAsSearched(searchedImage: String): ResultWrapper<LiveData<ImgurResponse>> {
        val imageResponse: MutableLiveData<ImgurResponse> = MutableLiveData()
        val deferredResponse = service.fetchSearchedImageResponse(searchedImage)
        return try {
            if (deferredResponse.isSuccessful && deferredResponse.body()?.success == true) {
                imageResponse.postValue(deferredResponse.body())
                ResultWrapper.SUCCESS(imageResponse)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }
}
