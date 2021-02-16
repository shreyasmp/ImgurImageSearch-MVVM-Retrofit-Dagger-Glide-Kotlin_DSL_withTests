package com.shreyas.imgurphotogallery.di

import android.app.Application
import android.content.Context
import com.shreyas.imgurphotogallery.di.modules.ImgurViewModelModule
import com.shreyas.imgurphotogallery.di.modules.ImgurViewModule
import com.shreyas.imgurphotogallery.di.modules.ServiceModule
import com.shreyas.imgurphotogallery.di.modules.ViewModelFactoryModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ServiceModule::class,
        ImgurViewModule::class,
        ImgurViewModelModule::class
    ]
)
abstract class ImgurModule {

    companion object {
        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }
}