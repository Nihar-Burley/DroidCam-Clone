# CamLink Local

CamLink Local is a privacy-first Android-to-Windows webcam system. This repository starts with reproducible Android, .NET, CMake, protocol, test, tooling, and CI foundations; product implementation follows in later milestones.

## Bootstrap

Install JDK 17, Android SDK platform 35, Gradle 8.10.2, CMake 3.25+, Ninja, Visual Studio 2022 Build Tools (C++ workload), and .NET SDK 10.0. The runtime product does not use Docker or cloud services.

```powershell
# Android
Set-Location android
gradle --no-daemon :app:assembleDebug :app:testDebugUnitTest ktfmtCheck
Set-Location ..

# Windows native and .NET scaffolds
cmake --preset windows-x64-debug -S windows
cmake --build windows/out/build/windows-x64-debug
ctest --test-dir windows/out/build/windows-x64-debug --output-on-failure
dotnet build windows/CamLink.sln --configuration Release
```

`VERSION` is the single release version. See [DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md) for validation, [CODING_STANDARDS.md](CODING_STANDARDS.md) for quality gates, and [Documents/README.md](Documents/README.md) for the product architecture.

## Layout

```text
android/             Android application and build conventions
windows/             .NET desktop and native CMake targets
shared/protocol/     Versioned cross-platform protocol contracts
tests/               Test ownership and execution guidance
tools/               Reproducible developer and release utilities
.github/workflows/   Verification and tagged-release automation
```
