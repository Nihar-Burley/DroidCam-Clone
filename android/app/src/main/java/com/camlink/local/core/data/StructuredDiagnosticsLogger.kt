package com.camlink.local.core.data

import android.util.Log
import com.camlink.local.core.domain.diagnostics.DiagnosticEvent
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class StructuredDiagnosticsLogger @Inject constructor() : DiagnosticsLogger {
  private val buffer = CopyOnWriteArrayList<DiagnosticEvent>()
  private val eventState = MutableStateFlow<List<DiagnosticEvent>>(emptyList())

  override val events: Flow<List<DiagnosticEvent>> = eventState.asStateFlow()

  override fun info(message: String) {
    Log.i("CamLink", message)
    buffer += DiagnosticEvent(System.currentTimeMillis(), message)
    eventState.value = buffer.takeLast(50)
  }
}
