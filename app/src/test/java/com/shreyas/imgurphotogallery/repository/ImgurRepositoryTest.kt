package com.shreyas.imgurphotogallery.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shreyas.imgurphotogallery.base.MockServerBaseTest
import com.shreyas.imgurphotogallery.service.IImgurPhotoService
import com.shreyas.imgurphotogallery.utils.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ImgurRepositoryTest : MockServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    override fun isMockServerEnabled(): Boolean = true

    private lateinit var repository: ImgurRepository
    private lateinit var service: IImgurPhotoService

    @Before
    fun start() {
        service = provideTestImgurImageService()
        repository = ImgurRepository(service)
    }

    @Test
    fun `given response as OK when fetch imgur image results in full list`() {
        runBlocking {
            mockHttpResponseFromFile("imgur_response_success.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getImgurImageListAsSearched("dogs")) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertThat(response).isNotNull()
                    assertThat(response?.data?.size).isEqualTo(60)
                }
            }
        }
    }

    @Test
    fun `given response as OK when fetch imagur image results in empty list`() {
        runBlocking {
            mockHttpResponseFromFile("imgur_response_not_success.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getImgurImageListAsSearched("dogs")) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertThat(response).isNotNull()
                    assertThat(response?.data?.size).isEqualTo(0)
                }
            }
        }
    }

    @Test
    fun `given response as SUCCESS while fetching image search list`() {
        runBlocking {
            mockHttpResponseFromFile(
                    "imgur_response_not_success.json",
                    HttpURLConnection.HTTP_NOT_FOUND
            )
            when (val result = repository.getImgurImageListAsSearched("dogs")) {
                is ResultWrapper.SUCCESS -> {
                    assertThat(result.value.value).isNotNull()
                    assertThat(result.value.value?.status).isEqualTo(404)
                }
            }
        }
    }

    @Test
    fun `given response as FAILURE while fetch image list results in exception`() {
        runBlocking {
            mockHttpResponse(403)
            when (val result = repository.getImgurImageListAsSearched("dogs")) {
                is ResultWrapper.FAILURE -> {
                    assertNotNull(result)
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertThat(expectedResponse.code).isEqualTo((result).code)
                }
            }
        }
    }
}