package com.shreyas.imgurphotogallery.base

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.shreyas.imgurphotogallery.repository.ImgurRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@ExperimentalCoroutinesApi
class CoRoutinesTestRule : TestRule {

    private val testCoRoutineDispatcher = TestCoroutineDispatcher()
    private val testCoRoutineScope = TestCoroutineScope(testCoRoutineDispatcher)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoRoutineDispatcher)
            base?.evaluate()
            Dispatchers.resetMain()
            testCoRoutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoRoutineScope.runBlockingTest { block() }
}

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(RobolectricTestRunner::class)
abstract class BaseViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoRoutinesTestRule()

    val application: Application = ApplicationProvider.getApplicationContext()

    @Mock
    lateinit var repository: ImgurRepository

    @Before
    open fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    open fun tearDown() {
        // Nothing here, so far
    }
}