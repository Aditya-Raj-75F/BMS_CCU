package com.example.bms_ccu.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.bms_ccu.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MainActivityTest {
    lateinit var activityScenario: ActivityScenario<MainActivity>

    @BeforeEach
    fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Nested
    inner class TestUiComponenetsVisibility {
        @Test
        @DisplayName("Checking Visibility for the Main Activity")
        fun test_isUiVisible() {
            onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Checking Visibility for the Hamburger Menu")
        fun test_isHamburgerIconVisible() {
            onView(withId(R.id.hamburgerMenu)).check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Checking Visibility of 75F Logo")
        fun test_isCompanyLogoVisible() {
            onView(withId(R.id.logo)).check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Checking Visibility for the toolbar")
        fun test_isToolbarPresent() {
            onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        }

        @Test
        @DisplayName("Checking Presence of TabLayout")
        fun test_isTabLayoutPresent() {
            onView(allOf(
                withId(R.id.tabLayout),
                isDescendantOfA(withId(R.id.toolbar)),
                hasDescendant(allOf(
                    withText("ZONES"),
                    withText("BUILDINGS"),
                    withText("SYSTEMS"),
                    withText("ALERTS")
                ))
            ))
        }
    }

    @Nested
    inner class TestMenuMovements {
        @Test
        fun test_sidebarMenu() {
                    onView(withId(R.id.sideMenuNavigation)).check(matches(not(isDisplayed())))
                    onView(withId(R.id.hamburgerMenu)).check(matches(isDisplayed())).perform(click())
                    onView(withId(R.id.sideMenuNavigation)).check(matches(isDisplayed()))
        }
    }
}