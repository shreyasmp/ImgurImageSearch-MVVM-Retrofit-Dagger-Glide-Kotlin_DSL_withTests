package com.shreyas.imgurphotogallery.di.modules

import com.shreyas.imgurphotogallery.view.ImageDetailFragment
import com.shreyas.imgurphotogallery.view.ImageSearchListFragment
import com.shreyas.imgurphotogallery.view.MainActivity
import com.shreyas.nycschools.di.annotations.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ImgurViewModule {

    // Activities here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    // Fragment here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesImageSearchListFragment(): ImageSearchListFragment

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesImageDetailFragment(): ImageDetailFragment
}