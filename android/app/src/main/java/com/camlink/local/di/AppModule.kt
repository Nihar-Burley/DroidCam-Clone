package com.camlink.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.camlink.local.core.data.PreferencesPeerRepository
import com.camlink.local.core.data.StructuredDiagnosticsLogger
import com.camlink.local.core.domain.diagnostics.DiagnosticsLogger
import com.camlink.local.core.domain.peer.PeerRepository
import com.camlink.local.core.domain.session.SessionController
import com.camlink.local.streaming.LocalSessionController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindings {
  @Binds abstract fun bindPeers(implementation: PreferencesPeerRepository): PeerRepository
  @Binds abstract fun bindDiagnostics(implementation: StructuredDiagnosticsLogger): DiagnosticsLogger
  @Binds abstract fun bindSession(implementation: LocalSessionController): SessionController
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("camlink_preferences") }
}
