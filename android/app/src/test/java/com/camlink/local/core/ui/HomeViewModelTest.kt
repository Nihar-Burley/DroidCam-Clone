package com.camlink.local.core.ui

import androidx.lifecycle.SavedStateHandle
import com.camlink.local.core.domain.diagnostics.DiagnosticEvent
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import com.camlink.local.core.domain.session.SessionController
import com.camlink.local.core.domain.session.SessionResult
import com.camlink.local.core.domain.session.SessionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
  private val dispatcher = StandardTestDispatcher()

  @Before fun setUp() { Dispatchers.setMain(dispatcher) }
  @After fun tearDown() { Dispatchers.resetMain() }

  @Test
  fun `starting pairing delegates to domain port`() = runTest {
    val controller = RecordingSessionController()
    val viewModel = HomeViewModel(controller, NoOpLogger(), SavedStateHandle())
    dispatcher.scheduler.advanceUntilIdle()
    assertEquals(SessionState.PAIRING, controller.state.value)
  }
}

private class RecordingSessionController : SessionController {
  override val state = MutableStateFlow(SessionState.IDLE)
  override suspend fun beginPairing(): SessionResult = SessionResult.Success.also { state.value = SessionState.PAIRING }
  override suspend fun startStreaming(): SessionResult = SessionResult.Success.also { state.value = SessionState.STREAMING }
  override suspend fun stopStreaming() { state.value = SessionState.IDLE }
}

private class NoOpLogger : DiagnosticsLogger {
  override val events: Flow<List<DiagnosticEvent>> = MutableStateFlow(emptyList())
  override fun info(message: String) = Unit
}
