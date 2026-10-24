package com.camlink.local.navigation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.weight
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.camlink.local.core.ui.ControlsScreen
import com.camlink.local.core.ui.DevicesScreen
import com.camlink.local.core.ui.DiagnosticsScreen
import com.camlink.local.core.ui.HomeScreen
import com.camlink.local.core.ui.PairScreen
import com.camlink.local.core.ui.PermissionScreen

private object Route {
  const val HOME = "home"
  const val PAIR = "pair"
  const val PERMISSION = "permission"
  const val CONTROLS = "controls"
  const val DEVICES = "devices"
  const val DIAGNOSTICS = "diagnostics"
}

@Composable
fun AppNavGraph() {
  val navController = rememberNavController()
  val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
    if (result.values.all { it }) navController.navigate(Route.CONTROLS) else navController.navigate(Route.PERMISSION)
  }
  val destinations = remember { listOf(Route.HOME to "Home", Route.DEVICES to "Devices", Route.DIAGNOSTICS to "Diagnostics") }
  val currentBackStack by navController.currentBackStackEntryAsState()

  Column(Modifier.fillMaxSize()) {
    NavHost(navController, startDestination = Route.HOME, modifier = Modifier.weight(1f)) {
      composable(Route.HOME) { HomeScreen(onPair = { navController.navigate(Route.PAIR) }, onControls = { navController.navigate(Route.PERMISSION) }) }
      composable(Route.PAIR) { PairScreen(onPermission = { navController.navigate(Route.PERMISSION) }) }
      composable(Route.PERMISSION) {
        PermissionScreen(onGranted = {
          permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
        })
      }
      composable(Route.CONTROLS) { ControlsScreen() }
      composable(Route.DEVICES) { DevicesScreen() }
      composable(Route.DIAGNOSTICS) { DiagnosticsScreen() }
    }
    NavigationBar {
      destinations.forEach { (route, label) ->
        NavigationBarItem(
          selected = currentBackStack?.destination?.route == route,
          onClick = { navController.navigate(route) { launchSingleTop = true } },
          icon = {},
          label = { Text(label) },
        )
      }
    }
  }
}
