package com.shreyas.imgurphotogallery.view

import com.google.common.truth.Truth.assertThat
import com.shreyas.imgurphotogallery.util.TestJsonUtils.startFragment
import com.shreyas.nycschools.runner.ImgurRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(ImgurRobolectricTestRunner::class)
class ImageDetailFragmentTest {

    private lateinit var imageDetailFragment: ImageDetailFragment

    @Before
    fun setUp() {
        imageDetailFragment = ImageDetailFragment()
    }

    @Test
    fun `test if image detail fragment created`() {
        startFragment(imageDetailFragment)
        assertThat(imageDetailFragment).isNotNull()
    }
}