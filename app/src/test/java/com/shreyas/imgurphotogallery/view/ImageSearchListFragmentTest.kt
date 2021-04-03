package com.shreyas.imgurphotogallery.view

import android.content.Context
import android.content.res.Resources
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import com.shreyas.imgurphotogallery.util.TestJsonUtils.startFragment
import com.shreyas.nycschools.runner.ImgurRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify

@RunWith(ImgurRobolectricTestRunner::class)
class ImageSearchListFragmentTest {

    private lateinit var imageSearchListFragment: ImageSearchListFragment

    private val mockContext = Mockito.mock(Context::class.java)
    private val mockMenu = Mockito.mock(Menu::class.java)
    private val mockMenuInflater = Mockito.mock(MenuInflater::class.java)
    private val mockMenuItem = Mockito.mock(MenuItem::class.java)
    private val mockResources = Mockito.mock(Resources::class.java)
    private val mockSearchView = Mockito.mock(SearchView::class.java)

    @Before
    fun setUp() {
        imageSearchListFragment = ImageSearchListFragment()
    }

    @Test
    fun `test if image search list fragment created`() {
        startFragment(imageSearchListFragment)
        assertThat(imageSearchListFragment).isNotNull()
    }

    @Test
    fun `test options menu has search option`() {
        doNothing().whenever(mockMenuInflater).inflate(anyInt(), Mockito.any(Menu::class.java))
        Mockito.`when`(mockMenu.findItem(anyInt())).thenReturn(mockMenuItem)
        Mockito.`when`(mockMenuItem.actionView).thenReturn(mockSearchView)
        Mockito.`when`(mockContext.resources).thenReturn(mockResources)
        Mockito.`when`(mockResources.getString(anyInt())).thenReturn("Search")

        imageSearchListFragment.onCreateOptionsMenu(mockMenu, mockMenuInflater)

        verify(mockMenuInflater).inflate(anyInt(), eq(mockMenu))
        verify(mockMenu).findItem(anyInt())
        verify(mockMenuItem).actionView
    }
}