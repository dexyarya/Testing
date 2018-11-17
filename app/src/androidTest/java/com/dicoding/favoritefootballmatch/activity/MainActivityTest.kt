package com.dicoding.favoritefootballmatch.activity

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.pressBack
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.dicoding.favoritefootballmatch.R.id.*

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(withId(recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(action_favourite)).check(matches(isDisplayed()))
        onView(withId(action_favourite)).perform(click())
        Espresso.pressBack()
        onView(withId(nextMatch)).check(matches(isDisplayed()))
        onView(withId(nextMatch)).perform(click())
        Thread.sleep(5000)
        onView(withId(recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(action_favourite)).check(matches(isDisplayed()))
        onView(withId(action_favourite)).perform(click())
        Espresso.pressBack()
        onView(withId(favorites)).check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
        Thread.sleep(3000)
        onView(withId(recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(action_favourite)).check(matches(isDisplayed()))
        onView(withId(action_favourite)).perform(click())
    }
}