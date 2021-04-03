package com.shreyas.imgurphotogallery.view

import android.os.Build
import android.os.Looper
import android.view.View
import androidx.test.core.app.ActivityScenario.launch
import com.google.common.truth.Truth.assertThat
import com.shreyas.imgurphotogallery.R
import com.shreyas.nycschools.runner.ImgurRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(ImgurRobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.LEGACY)
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `activity is successfully created`() {
        assertThat(activity).isNotNull()
    }

    @Test
    fun `recreate activity`() {
        val test = launch(MainActivity::class.java)
        test.recreate()
        assertThat(test).isNotNull()
    }

    @Test
    fun `should have correct app name`() {
        assertThat(activity.resources.getString(R.string.app_name)).isEqualTo("ImgurPhotoGallery")
    }

    @Test
    fun `test activity view components are visible`() {
        assertThat(activity.binding.mainLayout.visibility).isEqualTo(View.VISIBLE)
        assertThat(activity.binding.mainToolbar.visibility).isEqualTo(View.VISIBLE)
    }
}