import com.android.build.api.dsl.ApplicationExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

extensions.configure<ApplicationExtension> {
  namespace = "com.camlink.local"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.camlink.local"
    minSdk = 29
    targetSdk = 35
    versionCode = 1
    versionName = rootProject.file("../VERSION").readText().trim()
  }

  buildFeatures {
    buildConfig = true
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
}
