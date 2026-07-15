# Developer Guide

## Reproducible toolchain

Use JDK 17, Android SDK 35, Gradle 8.10.2, CMake 3.25+, Ninja, Visual Studio 2022 Build Tools, and .NET SDK 10.0. CI provisions these versions; use the same major/minor versions locally. Do not substitute containers, cloud runtimes, or unversioned package installs for the product build.

## Validation

Run the commands in [README.md](README.md) from a clean checkout. The CTest suite verifies that `windows/CMakePresets.json` has the expected schema and debug preset. Add protocol fixtures beside each schema and keep platform tests with their owning module.

## Secrets and diagnostics

Signing materials, certificates, keystores, packet captures, logs, media, and crash diagnostics are ignored by Git. CI signing is intentionally deferred until release credentials are provisioned; fork builds must remain unsigned.
