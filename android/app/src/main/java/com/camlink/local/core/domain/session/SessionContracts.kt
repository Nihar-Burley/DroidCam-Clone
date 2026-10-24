package com.camlink.local.core.domain.session

import kotlinx.coroutines.flow.StateFlow

enum class SessionState { IDLE, PAIRING, READY, STREAMING, ERROR }

sealed interface CamLinkError {
  val message: String

  data object PermissionDenied : CamLinkError { override val message = "Camera and microphone access is required." }
  data object ServiceUnavailable : CamLinkError { override val message = "Streaming service is unavailable." }
  data class Unexpected(override val message: String) : CamLinkError
}

sealed interface SessionResult {
  data object Success : SessionResult
  data class Failure(val error: CamLinkError) : SessionResult
}

interface SessionController {
  val state: StateFlow<SessionState>
  suspend fun beginPairing(): SessionResult
  suspend fun startStreaming(): SessionResult
  suspend fun stopStreaming()
}
