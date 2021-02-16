package com.shreyas.imgurphotogallery.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shreyas.imgurphotogallery.repository.ImgurRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

open class BaseViewModel @Inject constructor(val repository: ImgurRepository) : ViewModel() {

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }

    val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    var imageJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        imageJob?.cancel()
        _isError.postValue(false)
    }
}