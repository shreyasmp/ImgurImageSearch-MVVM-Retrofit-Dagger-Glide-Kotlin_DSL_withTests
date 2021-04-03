package com.shreyas.imgurphotogallery.di.modules

import androidx.lifecycle.ViewModel
import com.shreyas.imgurphotogallery.viewmodel.ImageSearchViewModel
import com.shreyas.imgurphotogallery.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ImgurViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImageSearchViewModel::class)
    abstract fun bindImageSearchViewModel(imageSearchViewModel: ImageSearchViewModel): ViewModel
}