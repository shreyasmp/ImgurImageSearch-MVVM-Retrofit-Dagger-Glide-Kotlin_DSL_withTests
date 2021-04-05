package com.shreyas.imgurphotogallery.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.shreyas.imgurphotogallery.base.BaseViewModelTest
import com.shreyas.imgurphotogallery.model.ImgurResponse
import com.shreyas.imgurphotogallery.util.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.imgurphotogallery.util.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.Shadows
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class ImageSearchViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: ImageSearchViewModel

    @Mock
    private lateinit var imageListResponseObserver: Observer<ImgurResponse>

    @Before
    override fun setup() {
        super.setup()
        viewModel = ImageSearchViewModel(repository)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `test image list live data is empty`() {
        val searchedImageLiveData = viewModel.imageSearchResponse.testObserver()
        assertThat(searchedImageLiveData.observedValues).isEmpty()
    }

    @Test
    fun `test image list live data success`() {
        val searchedImageList = getObjectFromJsonFile(
            jsonFile = "imgur_response_success.json",
            tClass = ImgurResponse::class.java
        )
        viewModel._imageSearchResponse.value = searchedImageList
        val imageSearchedListLiveData = viewModel.imageSearchResponse.testObserver()
        assertThat(imageSearchedListLiveData.observedValues).containsExactly(searchedImageList)
    }

    @Test
    fun `test image list live data with error`() {
        val searchedImageList = null
        viewModel._imageSearchResponse.value = searchedImageList
        val imageSearchedLiveData = viewModel.imageSearchResponse.testObserver()
        assertThat(imageSearchedLiveData.observedValues).containsExactly(searchedImageList)
    }

    @Test
    fun `fetch imgur image list http call success`() {
        val imgurList = MutableLiveData<ImgurResponse>().value
        coroutineTestRule.runBlockingTest {
            viewModel.imageSearchResponse.observeForever(imageListResponseObserver)
            whenever(repository.getImgurImageListAsSearched("dogs")).thenAnswer {
                imgurList
            }
            viewModel.fetchSearchedImageList("dogs")
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertThat(viewModel.imageSearchResponse.value).isNull()
            assertThat(viewModel.imageSearchResponse.value).isEqualTo(imgurList)

            verify(
                imageListResponseObserver,
                times(0)
            ).onChanged(viewModel.imageSearchResponse.value)
        }
    }

    @Test
    fun `fetch imgur image list http call error`() {
        val exception = Mockito.mock(HttpException::class.java)
        coroutineTestRule.runBlockingTest {
            viewModel.imageSearchResponse.observeForever(imageListResponseObserver)
            whenever(repository.getImgurImageListAsSearched("dogs")).thenAnswer {
                exception.message()
            }
            viewModel.fetchSearchedImageList("dogs")
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertThat(viewModel.imageSearchResponse.value).isNull()
            assertThat(viewModel.imageSearchResponse.value).isEqualTo(exception.message())

            verify(
                imageListResponseObserver,
                times(0)
            ).onChanged(viewModel.imageSearchResponse.value)
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        viewModel.imageSearchResponse.removeObserver(imageListResponseObserver)
    }
}