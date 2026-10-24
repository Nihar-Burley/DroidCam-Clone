package com.camlink.local.streaming

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StreamingService : Service() {
  @Inject lateinit var logger: DiagnosticsLogger

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    createChannel()
    startForeground(NOTIFICATION_ID, NotificationCompat.Builder(this, CHANNEL_ID)
      .setSmallIcon(android.R.drawable.presence_video_online)
      .setContentTitle("CamLink Local")
      .setContentText("Streaming boundary active")
      .setOngoing(true)
      .build())
    logger.info("streaming_service_started")
    return START_NOT_STICKY
  }

  override fun onDestroy() {
    logger.info("streaming_service_stopped")
    super.onDestroy()
  }

  override fun onBind(intent: Intent?): IBinder? = null

  private fun createChannel() {
    val manager = getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "Streaming", NotificationManager.IMPORTANCE_LOW))
  }

  private companion object {
    const val CHANNEL_ID = "camlink_streaming"
    const val NOTIFICATION_ID = 1001
  }
}
