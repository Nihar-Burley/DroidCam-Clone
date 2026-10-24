package com.camlink.local

import android.Manifest
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.camlink.local.streaming.StreamingService
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StreamingServiceTest {
  @get:Rule
  val permissions: GrantPermissionRule = GrantPermissionRule.grant(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO,
  )

  @Test
  fun serviceStartsAndStopsAtForegroundBoundary() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val intent = Intent(context, StreamingService::class.java)
    context.startForegroundService(intent)
    check(context.stopService(intent))
  }
}
