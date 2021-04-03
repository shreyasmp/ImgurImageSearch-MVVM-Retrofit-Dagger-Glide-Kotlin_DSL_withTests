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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.LooperMode

@RunWith(ImgurRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class ImagesRecyclerViewAdapterTest {

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
        adapter.setImgurImages(loadImages())
        assertThat(adapter.itemCount).isEqualTo(loadImages().size)
    }

    @Test
    fun `test if adapter and view holder is not null as mockito mock`() {
        assertThat(createViewHolder()).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val viewHolder = createViewHolder()
        adapter.imageList = loadImages()
        adapter.onBindViewHolder(viewHolder, 0)
        viewHolder.binding.searchedImageDescription.text = loadImages()[0].description

        assertThat(viewHolder.binding.searchedImageDescription.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.searchedImageDescription.text).isEqualTo("It's adorable!!!")

        assertThat(viewHolder.binding.imageListCard.performClick())
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