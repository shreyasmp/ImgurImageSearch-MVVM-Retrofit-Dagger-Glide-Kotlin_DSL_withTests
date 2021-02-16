package com.shreyas.imgurphotogallery.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.imgurphotogallery.base.BaseViewModel
import com.shreyas.imgurphotogallery.model.ImgurResponse
import com.shreyas.imgurphotogallery.repository.ImgurRepository
import com.shreyas.imgurphotogallery.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageSearchViewModel @Inject constructor(repository: ImgurRepository) :
    BaseViewModel(repository) {

    companion object {
        private val TAG = ImageSearchViewModel::class.java.simpleName
    }

    val _imageSearchResponse: MutableLiveData<ImgurResponse> = MutableLiveData()
    val imageSearchResponse: LiveData<ImgurResponse> = _imageSearchResponse

    fun fetchSearchedImageList(searchedString: String) {
        imageJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getImgurImageListAsSearched(searchedString)
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    _isError.value = false
                    _imageSearchResponse.value = result.value.value
                    Log.i(TAG, "Image List Response: ${result.value.value}")
                }
                is ResultWrapper.FAILURE -> {
                    _isError.value = true
                    _imageSearchResponse.value = null
                }
            }
        }
    }
}