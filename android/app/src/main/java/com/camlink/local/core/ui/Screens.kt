package com.camlink.local.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.camlink.local.core.domain.session.SessionState

@Composable
fun ScreenScaffold(title: String, content: @Composable () -> Unit) {
  Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    Text(title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.semantics { heading() })
    content()
  }
}

@Composable
fun HomeScreen(onPair: () -> Unit, onControls: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
  val state = viewModel.state.collectAsStateWithLifecycle()
  ScreenScaffold("CamLink Local") {
    Text("Session: ${state.value.name.lowercase()}")
    Button(onClick = { viewModel.startPairing(); onPair() }, modifier = Modifier.fillMaxWidth()) { Text("Pair a device") }
    Button(onClick = onControls, modifier = Modifier.fillMaxWidth()) { Text("Open controls") }
  }
}

@Composable
fun PairScreen(onPermission: () -> Unit) = ScreenScaffold("Pair device") {
  Text("Use a trusted local connection. Pairing secrets are never stored in app preferences.")
  Button(onClick = onPermission, modifier = Modifier.fillMaxWidth()) { Text("Continue") }
}

@Composable
fun PermissionScreen(onGranted: () -> Unit) = ScreenScaffold("Camera and microphone") {
  Text("CamLink needs camera and microphone access before it can stream. You can retry after denying access.")
  Button(onClick = onGranted, modifier = Modifier.fillMaxWidth()) { Text("Allow permissions") }
}

@Composable
fun ControlsScreen(viewModel: HomeViewModel = hiltViewModel()) {
  val state = viewModel.state.collectAsStateWithLifecycle()
  ScreenScaffold("Controls") {
    Text(if (state.value == SessionState.STREAMING) "Streaming boundary is active." else "Streaming is stopped.")
    Button(onClick = viewModel::toggleStream, modifier = Modifier.fillMaxWidth()) {
      Text(if (state.value == SessionState.STREAMING) "Stop streaming" else "Start streaming")
    }
  }
}

@Composable
fun DevicesScreen(viewModel: DevicesViewModel = hiltViewModel()) {
  val peers = viewModel.peers.collectAsStateWithLifecycle()
  ScreenScaffold("Devices") { Text(if (peers.value.isEmpty()) "No paired devices yet." else peers.value.joinToString { it.displayName }) }
}

@Composable
fun DiagnosticsScreen(viewModel: DiagnosticsViewModel = hiltViewModel()) {
  val events = viewModel.events.collectAsStateWithLifecycle()
  ScreenScaffold("Diagnostics") { Text(if (events.value.isEmpty()) "No diagnostic events yet." else events.value.takeLast(5).joinToString("\n") { it.message }) }
}
