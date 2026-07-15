# Developer Guide

## Objective

Enable deterministic local development while preserving platform boundaries.

## Setup and workflow

Install Android Studio/JDK 21, Android SDK 35, .NET 10 SDK, Visual Studio C++ desktop workload, Windows SDK, CMake/Ninja, Android platform-tools, and OBS SDK matching the supported OBS major version. Work in feature branches; run targeted unit tests before integration. Use a real Android 10+ phone and Windows x64 PC for media testing.

```powershell
./gradlew :android:app:testDebugUnitTest
dotnet test windows/CamLink.Desktop.Tests
ctest --preset windows-x64-debug
adb devices
```

## Boundaries and conventions

Protocol changes require schema fixtures in `shared/`, compatibility tests, and an ADR if they alter trust, media, or deployment. UI code invokes domain use cases only. Codecs and sockets must publish bounded, cancellable streams; callbacks must not block. Native interfaces use RAII, explicit ownership, and HRESULT-to-domain error mapping. Do not add cloud SDKs, tracking SDKs, or new kernel drivers without an approved ADR.

## Debugging

Enable diagnostic logging only for the smallest affected component. Inspect connection state, negotiated profile, queue depth, and keyframe requests before collecting logs. Never commit signing materials, QR payloads, paired-key stores, or production diagnostics.
