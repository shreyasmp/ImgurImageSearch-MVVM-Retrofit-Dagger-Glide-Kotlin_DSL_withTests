package com.shreyas.imgurphotogallery.di

import com.google.common.truth.Truth.assertThat
import com.shreyas.imgurphotogallery.MainApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant

class ImgurModuleTest {

    private val mockApplication = mockk<MainApplication>()
    private lateinit var module: ImgurModule

    @Before
    fun setUp() {
        module = FakeImgurModule()
    }

    @Test
    fun `test provide context returns application context`() {
        every { mockApplication.applicationContext } returns mockApplication

        val result = ImgurModule.provideApplicationContext(mockApplication)

        assertThat(result).isEqualTo(mockApplication)
        verify { mockApplication.applicationContext }

        Instant.now()
    }

    class FakeImgurModule : ImgurModule() {

    }
}