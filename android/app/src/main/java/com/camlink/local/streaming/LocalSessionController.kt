package com.camlink.local.streaming

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import com.camlink.local.core.domain.session.SessionController
import com.camlink.local.core.domain.session.SessionResult
import com.camlink.local.core.domain.session.SessionState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class LocalSessionController @Inject constructor(
  @ApplicationContext private val context: Context,
  private val logger: DiagnosticsLogger,
) : SessionController {
  private val mutableState = MutableStateFlow(SessionState.IDLE)
  override val state: StateFlow<SessionState> = mutableState

  override suspend fun beginPairing(): SessionResult {
    mutableState.value = SessionState.PAIRING
    logger.info("pairing_started")
    return SessionResult.Success
  }

  override suspend fun startStreaming(): SessionResult = try {
    ContextCompat.startForegroundService(context, Intent(context, StreamingService::class.java))
    mutableState.value = SessionState.STREAMING
    logger.info("streaming_requested")
    SessionResult.Success
  } catch (error: SecurityException) {
    logger.info("streaming_permission_denied")
    SessionResult.Failure(com.camlink.local.core.domain.session.CamLinkError.PermissionDenied)
  } catch (error: Exception) {
    logger.info("streaming_service_unavailable")
    SessionResult.Failure(com.camlink.local.core.domain.session.CamLinkError.ServiceUnavailable)
  }

  override suspend fun stopStreaming() {
    context.stopService(Intent(context, StreamingService::class.java))
    mutableState.value = SessionState.IDLE
    logger.info("streaming_stopped")
  }
}
