package com.shreyas.imgurphotogallery.view

import android.view.View
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import com.shreyas.imgurphotogallery.model.Images
import com.shreyas.imgurphotogallery.model.ImgurResponse
import com.shreyas.imgurphotogallery.util.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.imgurphotogallery.view.adapter.ImagesRecyclerViewAdapter
import com.shreyas.imgurphotogallery.view.callback.ImageListItemClickListener
import com.shreyas.nycschools.runner.ImgurRobolectricTestRunner
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.LooperMode

@RunWith(ImgurRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class ImagesRecyclerViewAdapterTest {

    private val mockImageListAdapter = mockk<ImagesRecyclerViewAdapter>()
    private val mockImageListAdapterHolder =
        mock(ImagesRecyclerViewAdapter.ImageViewHolder::class.java)
    private val mockImageList = mock(ArrayList<Images>()::class.java)
    private val mockListener = mock(ImageListItemClickListener::class.java)
    private lateinit var adapter: ImagesRecyclerViewAdapter

    @Before
    fun setUp() {
        adapter = ImagesRecyclerViewAdapter(mockImageList, mockListener)
        loadImages()
    }

    @Test
    fun `test the school list adapter item count`() {
        every { mockImageListAdapter.setImgurImages(loadImages()) } just Runs
        every { mockImageListAdapter.itemCount } returns loadImages().size
        mockImageListAdapter.setImgurImages(loadImages())
        assertThat(mockImageListAdapter.itemCount).isEqualTo(loadImages().size)
        verify { mockImageListAdapter.setImgurImages(loadImages()) }
        verify { mockImageListAdapter.itemCount }
    }

    @Test
    fun `test if adapter and view holder is not null as mockk`() {
        val mockHolder = mockk<ImagesRecyclerViewAdapter.ImageViewHolder>()
        assertThat(mockImageListAdapter).isNotNull()
        assertThat(mockHolder).isNotNull()
    }

    @Test
    fun `test if adapter and view holder is not null as mockito mock`() {
        val mockAdapter = mock(ImagesRecyclerViewAdapter::class.java)
        assertThat(mockAdapter).isNotNull()
        assertThat(mockImageListAdapterHolder).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val mockHolder = createViewHolder()
        adapter.imageList = loadImages()
        adapter.onBindViewHolder(mockHolder, 0)
        mockHolder.binding.searchedImageDescription.text = loadImages()[0].description

        assertThat(mockHolder.binding.searchedImageDescription.visibility).isEqualTo(View.VISIBLE)
        assertThat(mockHolder.binding.searchedImageDescription.text).isEqualTo("It's adorable!!!")

        assertThat(mockHolder.binding.imageListCard.performClick())
    }

    private fun createViewHolder(): ImagesRecyclerViewAdapter.ImageViewHolder {
        val linearLayout = LinearLayout(getApplicationContext())
        return adapter.onCreateViewHolder(linearLayout, 0)
    }

    private fun loadImages(): MutableList<Images> {
        val imageResponse: ImgurResponse? = getObjectFromJsonFile(
            jsonFile = "imgur_response_success.json",
            ImgurResponse::class.java
        )
        val retrievedImageList = mutableListOf<Images>()
        if (imageResponse != null) {
            val iterator = imageResponse.data.listIterator()
            while (iterator.hasNext()) {
                val imageDetail = iterator.next()
                if (imageDetail.images != null && imageDetail.nsfw != null && !imageDetail.nsfw!!) {
                    for (images in imageDetail.images!!) {
                        retrievedImageList.add(images)
                    }
                }
            }
        }
        return retrievedImageList
    }
}