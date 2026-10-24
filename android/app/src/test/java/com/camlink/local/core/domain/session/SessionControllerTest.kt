package com.camlink.local.core.domain.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionControllerTest {
  @Test
  fun `streaming controller reports typed session state`() = runBlocking {
    val controller = FakeSessionController()
    controller.startStreaming()
    assertEquals(SessionState.STREAMING, controller.state.value)
    controller.stopStreaming()
    assertEquals(SessionState.IDLE, controller.state.value)
  }
}

private class FakeSessionController : SessionController {
  override val state = MutableStateFlow(SessionState.IDLE)
  override suspend fun beginPairing(): SessionResult = SessionResult.Success.also { state.value = SessionState.PAIRING }
  override suspend fun startStreaming(): SessionResult = SessionResult.Success.also { state.value = SessionState.STREAMING }
  override suspend fun stopStreaming() { state.value = SessionState.IDLE }
}
