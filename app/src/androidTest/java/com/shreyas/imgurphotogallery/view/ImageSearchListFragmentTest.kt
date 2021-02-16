package com.shreyas.imgurphotogallery.view

import android.os.SystemClock
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shreyas.imgurphotogallery.R
import com.shreyas.imgurphotogallery.view.adapter.ImagesRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ImageSearchListFragmentTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_initial_state_image_search() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)

        // Click on search icon
        onView(withId(R.id.action_search)).perform(click())

        // Type the text in search field and submit the query
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("dogs"),
            pressImeActionButton()
        )
        // Adding delay to ensure data is loaded since its a lot of images loaded
        SystemClock.sleep(3000)
        with(onView(withId(R.id.image_searched_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<ImagesRecyclerViewAdapter.ImageViewHolder>(1)
            )
            perform(click())
        }
    }

    @Test
    fun test_scroll_to_fifteenth_element_and_click() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        // Click on search icon
        onView(withId(R.id.action_search)).perform(click())

        // Type the text in search field and submit the query
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("hamster"),
            pressImeActionButton()
        )
        SystemClock.sleep(5000)
        with(onView(withId(R.id.image_searched_list))) {
            perform(
                RecyclerViewActions.actionOnItemAtPosition<ImagesRecyclerViewAdapter.ImageViewHolder>(
                    15,
                    click()
                )
            )
        }
    }
}