package com.camlink.local.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.camlink.local.core.domain.peer.Peer
import com.camlink.local.core.domain.peer.PeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesPeerRepository @Inject constructor(
  private val dataStore: DataStore<Preferences>,
) : PeerRepository {
  private val pairedDeviceName = booleanPreferencesKey("paired_device_placeholder")

  override val peers: Flow<List<Peer>> = dataStore.data.map { preferences ->
    if (preferences[pairedDeviceName] == true) listOf(Peer("placeholder", "Paired device", false)) else emptyList()
  }

  override suspend fun forget(peerId: String) {
    dataStore.edit { it.remove(pairedDeviceName) }
  }
}
