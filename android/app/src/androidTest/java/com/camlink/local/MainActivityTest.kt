package com.camlink.local

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.os.SystemClock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
  @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun appStartsAndRestoresHomeAfterRotation() {
    composeRule.onNodeWithText("CamLink Local").assertIsDisplayed()
    composeRule.activityRule.scenario.recreate()
    composeRule.onNodeWithText("CamLink Local").assertIsDisplayed()
  }

  @Test
  fun permissionScreenExplainsRetryAfterDenial() {
    composeRule.onNodeWithText("Pair a device").performClick()
    composeRule.onNodeWithText("Continue").performClick()
    composeRule.onNodeWithText("Allow permissions").assertIsDisplayed()
  }

  @Test
  fun startupRendersHomeWithinBudget() {
    val startedAt = SystemClock.elapsedRealtime()
    composeRule.onNodeWithText("CamLink Local").assertIsDisplayed()
    check(SystemClock.elapsedRealtime() - startedAt < 5_000) { "Startup exceeded scaffold budget." }
  }
}
