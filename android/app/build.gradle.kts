plugins {
  id("camlink.android.application")
  alias(libs.plugins.ktfmt)
}

android {
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
