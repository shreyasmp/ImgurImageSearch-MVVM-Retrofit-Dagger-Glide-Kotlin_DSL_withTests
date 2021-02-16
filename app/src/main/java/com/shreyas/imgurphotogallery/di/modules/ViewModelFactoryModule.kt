package com.shreyas.imgurphotogallery.di.modules

import androidx.lifecycle.ViewModelProvider
import com.shreyas.imgurphotogallery.di.DaggerViewModelFactory
import com.shreyas.imgurphotogallery.repository.IImgurRepository
import com.shreyas.imgurphotogallery.repository.ImgurRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bind(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun providesImgurPhotoRepository(repository: ImgurRepository): IImgurRepository
}