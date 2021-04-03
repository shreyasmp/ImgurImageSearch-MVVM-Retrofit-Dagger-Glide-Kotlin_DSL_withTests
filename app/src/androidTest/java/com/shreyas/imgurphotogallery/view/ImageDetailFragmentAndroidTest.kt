package com.shreyas.imgurphotogallery.view

import android.os.SystemClock
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
class ImageDetailFragmentAndroidTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_scroll_to_second_image_card_and_click() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        // Click on search icon
        onView(withId(R.id.action_search)).perform(ViewActions.click())

        // Type the text in search field and submit the query
        onView(ViewMatchers.isAssignableFrom(EditText::class.java)).perform(
            ViewActions.typeText("dogs"),
            ViewActions.pressImeActionButton()
        )
        SystemClock.sleep(5000)
        with(onView(withId(R.id.image_searched_list))) {
            perform(
                RecyclerViewActions.actionOnItemAtPosition<ImagesRecyclerViewAdapter.ImageViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
        }
    }

    @Test
    fun test_back_button_to_image_list() {
        test_scroll_to_second_image_card_and_click()
        SystemClock.sleep(6000)
        Espresso.pressBack()
        onView(withId(R.id.image_searched_list)).check(matches(isDisplayed()))
    }
}