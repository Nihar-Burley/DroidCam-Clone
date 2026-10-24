package com.camlink.local.core.domain.peer

import kotlinx.coroutines.flow.Flow

data class Peer(val id: String, val displayName: String, val isConnected: Boolean)

interface PeerRepository {
  val peers: Flow<List<Peer>>
  suspend fun forget(peerId: String)
}
