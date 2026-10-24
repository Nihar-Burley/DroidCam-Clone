package com.camlink.local.core.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import com.camlink.local.core.domain.peer.Peer
import com.camlink.local.core.domain.peer.PeerRepository
import com.camlink.local.core.domain.session.SessionController
import com.camlink.local.core.domain.session.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val session: SessionController,
  private val logger: DiagnosticsLogger,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  val state: StateFlow<SessionState> = session.state
  private val restoredRoute = savedStateHandle.get<String>("last_route") ?: "home"

  init { logger.info("home_opened:$restoredRoute") }

  fun startPairing() = viewModelScope.launch { session.beginPairing() }
  fun toggleStream() = viewModelScope.launch {
    if (state.value == SessionState.STREAMING) session.stopStreaming() else session.startStreaming()
  }
}

@HiltViewModel
class DevicesViewModel @Inject constructor(repository: PeerRepository) : ViewModel() {
  val peers: StateFlow<List<Peer>> = repository.peers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

@HiltViewModel
class DiagnosticsViewModel @Inject constructor(logger: DiagnosticsLogger) : ViewModel() {
  val events = logger.events.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
