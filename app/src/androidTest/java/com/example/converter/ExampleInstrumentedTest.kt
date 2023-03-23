package com.example.converter

import androidx.core.app.ActivityCompat
import androidx.test.espresso.DataInteraction.DisplayDataMatcher
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.converter.view.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
  //  @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.converter", appContext.packageName)
//    }

    @get:Rule
    val ActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldShowHeaderText(){
        Espresso.onView(ViewMatchers.withId(R.id.Header))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.Header)))
    }
    @Test
    fun shouldShowHeaderCountryName() {
        Espresso.onView(ViewMatchers.withId(R.id.btnLocation))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.countryText))
            .check(ViewAssertions.matches(ViewMatchers.withText("Russia")))
    }
}