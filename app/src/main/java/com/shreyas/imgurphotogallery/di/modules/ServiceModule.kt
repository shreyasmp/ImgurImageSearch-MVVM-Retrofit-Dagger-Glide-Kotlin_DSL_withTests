package com.shreyas.imgurphotogallery.di.modules

import com.shreyas.imgurphotogallery.service.IImgurPhotoService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ServiceModule {

    private const val IMGUR_BASE_URL = "https://api.imgur.com/3/gallery/search/time/1/"

    @Provides
    @Reusable
    internal fun provideImgurPhotoService(): IImgurPhotoService {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(IMGUR_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(IImgurPhotoService::class.java)
    }
}