# CamLink Local Master Execution Guide

## Product Contract

Build a free, local-first Android-to-Windows webcam system: Android 10+ Kotlin, Windows 10 22H2/11 x64, ADB USB, encrypted same-LAN Wi-Fi, H.264 video, Opus audio in a native OBS plugin, and a Media Foundation virtual camera. It has no account, backend, cloud, payment, analytics, or enabled-by-default telemetry. Defer virtual microphone, 4K/HDR, recording, AI, remote access, screen sharing, multi-device production, macOS/Linux/iOS, and Windows ARM64.

Success means QR pairing in under 60 seconds, stable 1080p30 on the Android 10 Snapdragon 720G-class baseline, USB p50 latency at most 100 ms, Wi-Fi p50 at most 180 ms on clean 5 GHz LAN, automatic reconnect, and working OBS plus virtual-camera evidence on the defined test matrix.

Repository layout: `android/{app,core-domain,core-data,core-ui,streaming}`, `windows/{desktop,host,virtual-camera,obs-plugin}`, `shared/protocol`, `tests/{integration,performance}`, `tools/{packaging,signing}`, `docs/adr`, and `.github/workflows`.

## AI Engineer Execution Contract (Copy Into Every Prompt)

You are the implementation owner. Execute the active Prompt ID completely; do not merely describe code, provide a plan, stop after scaffolding, replace production behavior with mocks, or ask avoidable questions. Inspect the repository and applicable `AGENTS.md` first. Preserve unrelated user work. Make only the scoped changes below, but add every necessary production file, test, configuration, and documentation update required for the feature to work end-to-end. If a requirement cannot be verified because hardware, signing assets, or a third-party SDK is unavailable, implement all verifiable work, run all possible checks, document the precise external blocker, and do not claim completion or commit.

Every prompt is standalone: read this guide, dependencies, ADRs, and current code before work. Use clean architecture, SOLID, MVVM/Hilt on Android, C++20 RAII and bounded ownership on Windows, dependency inversion, immutable state, cancellation, typed errors, and no pseudo-code, mocks in production paths, duplicated logic, debug remnants, or unapproved TODO/FIXME.

Every implementation adds structured crash-safe logs (Debug/Info/Warning/Error/Fatal), component tag, correlation/session IDs, timing, redaction, configurable verbosity, and 50 MiB/14-day rotation. Never log media, keys, QR payloads, or raw addresses by default. Validate all input/lengths/ranges before allocation; use TLS 1.3, QR-derived mutual authentication, Keystore, DPAPI, CSPRNGs, replay protection, version negotiation, dependency/secret scans, SAST, fuzzing, and privacy review.

Every prompt must state objective, outputs, files, decisions, implementation, error/logging behavior, test plan, performance/security requirements, acceptance/manual checks, CI/docs updates, completion criteria, pitfalls, and future compatibility. Run formatting, lint, static analysis, unit tests, affected integration tests, and relevant performance tests. Test failures, cancellation, retries, timeouts, recovery, and resource cleanup. Update README, architecture/API/protocol/developer/troubleshooting/release/changelog/known-issues docs when affected.

After validation, commit all relevant working-code changes. A prompt is incomplete until committed. Commit message format is `PMPT-xxxx: Imperative professional description`. Before committing run and report exact commands for formatter, lint/analyzers, build, unit tests, integration tests, and relevant benchmark; inspect `git diff --check`, status, TODO/FIXME, and secret scan. Confirm saved files, no debug code, no secrets, and relevant files only. Completion record: ID, title, created/modified files, tests/results, docs, build, commit hash/message, next ID. Do not commit if a gate fails.

## Week 1 — Android Foundation

> **Per-prompt execution requirement:** The active prompt must be copied together with the complete **AI Engineer Execution Contract** above. That contract is normative and is part of every PMPT block; it mandates full implementation, exact validation commands/results, documentation, and a successful ID-prefixed Git commit before advancing.

### PMPT-0001
**Week/Milestone:** 1/1.1. **Title:** Initialize reproducible monorepo. **Dependencies:** none. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** Create buildable Android, .NET/CMake, shared-protocol, test, tools, CI, license, `.editorconfig`, `.gitignore`, versioning, and documentation foundation. **Files:** create `android/settings.gradle.kts`, `android/build.gradle.kts`, `android/gradle/libs.versions.toml`, `android/build-logic/`, `windows/CMakeLists.txt`, `windows/CMakePresets.json`, `windows/CamLink.sln`, `shared/protocol/README.md`, `tests/README.md`, `tools/README.md`, `.github/workflows/{verify,release}.yml`, `.editorconfig`, `.gitignore`, `LICENSE`, and `VERSION`; modify `README.md`, `DEVELOPER_GUIDE.md`, `CODING_STANDARDS.md`, `GIT_WORKFLOW.md`, and `CHANGELOG.md`. **Decision:** no runtime Docker/cloud; pinned, reproducible toolchains.

**Implementation/quality:** Configure formatter, lint, SAST, secret/dependency/license scans, SBOM/checksum artifact jobs; ignore keystores/certificates/diagnostics. Build all empty targets from clean clone; test configuration parsing. **Pitfalls:** generated IDE state, secrets, unpinned tools. **Acceptance:** clean local and CI builds plus bootstrap instructions. **Commit:** `PMPT-0001: Initialize reproducible monorepo foundation`. **Next:** PMPT-0002.

### PMPT-0002
**Week/Milestone:** 1/1.2. **Title:** Scaffold Android clean architecture. **Dependencies:** PMPT-0001. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** Kotlin Android 10+ app with Material 3, MVVM, Hilt, navigation, DataStore, permissions, foreground-service boundary, Home/Pair/Controls/Devices/Diagnostics screens. **Files:** create `android/app/src/main/java/com/camlink/local/{App,MainActivity}.kt`, `di/AppModule.kt`, `navigation/AppNavGraph.kt`, domain contracts under `core-domain/{session,peer,diagnostics}`, DataStore adapters under `core-data`, Compose UI/ViewModels under `core-ui`, streaming service boundary under `streaming`, and matching JVM/instrumentation tests; modify Android manifest and Android build files only for required permissions/services. **Decision:** UI calls domain ports only; secrets never enter DataStore.

**Requirements:** typed errors, accessible permission UX, state restoration, cancellation, structured logs. Unit-test ViewModels/domain; instrument permission denial/retry, rotation, process recovery, service start/stop; measure startup. **Acceptance:** installs/navigates accessibly on Android 10 without capture yet. **Commit:** `PMPT-0002: Scaffold Android MVVM application architecture`. **Next:** PMPT-0003.

### PMPT-0003
**Week/Milestone:** 1/1.3. **Title:** Implement capability-aware CameraX capture. **Dependencies:** PMPT-0002. **Effort:** 1.5 days. **Status:** Pending.

**Objective/output:** CameraX lifecycle capture plus Camera2 interop capability model and front/rear selector. **Files:** create `streaming/camera/{CameraCapturePort,CameraXCaptureAdapter,CameraCapability,CameraSelectorPolicy,CaptureSessionState,CameraError}.kt`, domain camera use cases, `core-ui/controls/CameraControlsViewModel.kt`, tests/fixtures; modify streaming DI, `API.md`, `TESTING.md`, and `USER_MANUAL.md`. **Decision:** advertise only hardware-reported lenses/controls; release camera on lifecycle loss.

**Requirements:** validate ranges, serialize bind/unbind, map camera exceptions, redact IDs. Test unsupported/denied camera, rotation, background/foreground, switch, repeated bind/unbind, startup/memory. **Acceptance:** baseline device previews supported lenses safely. **Commit:** `PMPT-0003: Implement capability-aware CameraX capture`. **Next:** PMPT-0004.

### PMPT-0004
**Week/Milestone:** 1/1.4. **Title:** Add Camera2 control policy. **Dependencies:** PMPT-0003. **Effort:** 0.75 day. **Status:** Pending.

**Objective/output:** capability-gated zoom, focus, exposure, torch, mirror, orientation, and profile controls. **Files:** create `streaming/camera/{CameraControlPort,Camera2ControlAdapter}.kt`, `core-domain/camera/UpdateCameraControlUseCase.kt`, control UI state/composables/tests; modify DI, `CONFIGURATION_REFERENCE.md`, `USER_MANUAL.md`, and `TESTING.md`. **Decision:** normalized domain values, adapter clamps platform values; unsupported control is explanatory and inaccessible for action.

**Requirements:** coalesce sliders, cancel stale controls, persist only per paired device. Test rapid inputs, switch/rotate/error/reconnect. **Acceptance:** no unsupported hardware crash and predictable preview. **Commit:** `PMPT-0004: Add capability-gated camera controls`. **Next:** PMPT-0005.

### PMPT-0005
**Week/Milestone:** 1/1.5. **Title:** Implement MediaCodec H.264 encoder. **Dependencies:** PMPT-0003. **Effort:** 1.5 days. **Status:** Pending.

**Objective/output:** low-latency hardware surface encoder. **Files:** create `streaming/video/{VideoEncoderPort,MediaCodecVideoEncoder,VideoProfileNegotiator,EncodedVideoFrame,BoundedFrameQueue,KeyframeController}.kt`, tests, and `shared/protocol/test-vectors/h264`; modify streaming DI, `MEDIA_PIPELINES.md`, `PERFORMANCE.md`, and `COMMUNICATION_PROTOCOL.md`. **Decision:** 480p–1080p, 1–20 Mbps, 15/30/60 only by capability; 90 kHz monotonic PTS; repeat SPS/PPS at IDR.

**Requirements:** no UI blocking/unbounded queues; typed reset/reconfigure errors; validate dimensions/flags/lengths. Test profile fallback, saturation, keyframe requests, malformed output abstraction, cancellation and 30-minute 1080p30 CPU/RSS/battery/thermal run. **Acceptance:** valid H.264 reaches a test sink at documented baseline. **Commit:** `PMPT-0005: Implement bounded MediaCodec H.264 encoder`. **Next:** PMPT-0006.

### PMPT-0006
**Week/Milestone:** 1/1.6. **Title:** Implement Opus audio pipeline. **Dependencies:** PMPT-0002. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** AudioRecord to timestamped 48 kHz Opus for OBS only. **Files:** create `streaming/audio/{AudioCapturePort,AudioRecordAdapter,OpusEncoderPort,EncodedAudioFrame,AudioClock}.kt` and tests; modify streaming DI, audio permission UI, `MEDIA_PIPELINES.md`, and `TESTING.md`. **Decision:** bounded 20 ms frames; optional platform AEC/NS/AGC only when available and visible; no virtual audio driver. Test denial, route change, interruption, drift, cancellation, failure, and memory. **Acceptance:** valid packets/metrics without main-thread work. **Commit:** `PMPT-0006: Implement timestamped Opus audio pipeline`. **Next:** PMPT-0007.

### PMPT-0007
**Week/Milestone:** 1/1.7. **Title:** Define protocol and state machine. **Dependencies:** PMPT-0001. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** language-neutral CBOR protocol, 16-byte `magic/version/type/flags/sequence/length` header, lifecycle/error taxonomy and vectors. **Files:** schema, models, state diagram, fixtures, fuzz seeds, API/protocol docs. **Decision:** messages are HELLO, pair, start/stop, control, video/audio, heartbeat, error; 64 KiB control/2 MiB media caps; major reject/minor negotiate. Test both-language byte compatibility, malformed/oversize/replay/version cases. **Commit:** `PMPT-0007: Define versioned streaming protocol contracts`. **Next:** PMPT-0008.

## Week 2 — Trust, Transport, Host

> **Per-prompt execution requirement:** The active prompt must be copied together with the complete **AI Engineer Execution Contract** above. That contract is normative and is part of every PMPT block; it mandates full implementation, exact validation commands/results, documentation, and a successful ID-prefixed Git commit before advancing.

### PMPT-0008
**Week/Milestone:** 2/2.1. **Title:** Implement QR trust pairing. **Dependencies:** PMPT-0002, PMPT-0007. **Effort:** 1.25 days. **Status:** Pending.

**Objective/output:** offline dual-confirmation QR pairing, ECDH peer secret, revoke/re-pair lifecycle. **Files:** QR and pairing state, peer repository, Keystore/DPAPI adapters, migrations, tests/security docs. **Decision:** QR carries ephemeral host fingerprint, 256-bit single-use secret, protocol range, five-minute expiry. Test expiry/tamper/replay/revoke/reconnect; no plaintext key storage/logging. **Commit:** `PMPT-0008: Implement QR-based trusted device pairing`. **Next:** PMPT-0009.

### PMPT-0009
**Week/Milestone:** 2/2.2. **Title:** Implement authenticated TLS LAN transport. **Dependencies:** PMPT-0007, PMPT-0008. **Effort:** 1.5 days. **Status:** Pending.

**Objective/output:** private-LAN mDNS/manual-IP discovery and TLS 1.3 framed transport. **Files:** discovery/transport ports, handshake, policy, tests, firewall/docs. **Decision:** mDNS is never identity; pin QR bootstrap identity; one active stream, timeouts, 5 pair attempts/hour, 20 control requests/second. Test MITM/replay/invalid frame/manual IP/isolated LAN/cancellation. **Commit:** `PMPT-0009: Implement authenticated TLS LAN transport`. **Next:** PMPT-0010.

### PMPT-0010
**Week/Milestone:** 2/2.3. **Title:** Add ADB USB transport. **Dependencies:** PMPT-0007, PMPT-0008. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** ADB forwarding adapter reusing protocol/session code. **Files:** device/forwarding adapter, diagnostics, tests/troubleshooting. **Decision:** verify one authorized physical device; idempotent forward cleanup; retain app-layer peer authentication. Test unauthorized, timeout, replacement, unplug/replug; never interpolate untrusted data in shell commands. **Commit:** `PMPT-0010: Add ADB USB transport adapter`. **Next:** PMPT-0011.

### PMPT-0011
**Week/Milestone:** 2/2.4. **Title:** Build Media Foundation Host. **Dependencies:** PMPT-0007, PMPT-0009. **Effort:** 2 days. **Status:** Pending.

**Objective/output:** C++20 Host terminates sessions, decodes H.264/Opus, clocks A/V, and exposes restricted named-pipe IPC. **Files:** Host, decoders, bounded jitter buffers, local API, metrics, tests/docs. **Decision:** Host owns media; 60–120 ms jitter; 90 kHz/48 kHz timestamps; interactive-user pipe ACL; hardware decode first. Test golden decode, late/out-of-order frames, leaks, crash/restart, invalid IPC, 1080p30. **Commit:** `PMPT-0011: Build Media Foundation streaming Host`. **Next:** PMPT-0012.

### PMPT-0012
**Week/Milestone:** 2/2.5. **Title:** Add recovery and adaptation. **Dependencies:** PMPT-0005, PMPT-0006, PMPT-0009, PMPT-0011. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** heartbeat, quality feedback, bounded reconnect, adaptation state/UI. **Files:** orchestrators, policy, fault tests/perf docs. **Decision:** heartbeat each second; three misses reconnect 1–30 s for ≤10 min; adapt bitrate then FPS then resolution with hysteresis; disconnected placeholder replaces stale media. Test Wi-Fi/USB loss, oscillation, cleanup. **Commit:** `PMPT-0012: Implement resilient streaming session recovery`. **Next:** PMPT-0013.

## Week 3 — Windows Outputs

> **Per-prompt execution requirement:** The active prompt must be copied together with the complete **AI Engineer Execution Contract** above. That contract is normative and is part of every PMPT block; it mandates full implementation, exact validation commands/results, documentation, and a successful ID-prefixed Git commit before advancing.

### PMPT-0013
**Week/Milestone:** 3/3.1. **Title:** Build accessible WinUI companion. **Dependencies:** PMPT-0008, PMPT-0011, PMPT-0012. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** WinUI pairing, device, profile, controls, diagnostics UI via versioned Host IPC only. **Files:** views/ViewModels/pipe client/accessibility tests/docs. **Decision:** calls limited to status, peers, pairing, start/stop, profile/control, diagnostics. Require async cancellation, theme, resource keys, keyboard/screen reader/WCAG 2.2 AA; UI never accesses frame buffers. **Commit:** `PMPT-0013: Build accessible Windows companion UI`. **Next:** PMPT-0014.

### PMPT-0014
**Week/Milestone:** 3/3.2. **Title:** Implement virtual camera. **Dependencies:** PMPT-0011. **Effort:** 1.5 days. **Status:** Pending.

**Objective/output:** isolated C++ Media Foundation virtual camera with registration/sample delivery/disconnected frame. **Files:** camera target, installer contract, negotiation, VM tests/docs. **Decision:** Host IPC only, safe validated shared handles, modern MF; no DirectShow unless ADR. Test clean VM install, OBS/Zoom/Teams/Chrome enumeration, consumer restart, Host crash. **Commit:** `PMPT-0014: Implement Media Foundation virtual camera`. **Next:** PMPT-0015.

### PMPT-0015
**Week/Milestone:** 3/3.3. **Title:** Build OBS native source. **Dependencies:** PMPT-0006, PMPT-0011, PMPT-0012. **Effort:** 1.5 days. **Status:** Pending.

**Objective/output:** OBS source with Host video + synchronized 48 kHz audio and connection controls. **Files:** plugin target, source lifecycle, IPC client, settings, tests/docs. **Decision:** plugin neither pairs nor decodes network; non-blocking callbacks and release resources on destroy. Test add/remove/reload/reconnect/current+previous OBS. **Commit:** `PMPT-0015: Build native OBS streaming source plugin`. **Next:** PMPT-0016.

### PMPT-0016
**Week/Milestone:** 3/3.4. **Title:** Add end-to-end suite. **Dependencies:** PMPT-0010 through PMPT-0015. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** automation/fault injection for pairing to consumer output. **Files:** harness, hardware config, reports/tests/docs. Test USB/Wi-Fi, start/stop/revoke/reconnect, switch, sleep/resume, weak Wi-Fi, app/Host/OBS restart, old-version reject. **Acceptance:** reference hardware passes and regressions block CI. **Commit:** `PMPT-0016: Add end-to-end streaming integration coverage`. **Next:** PMPT-0017.

## Week 4 — Release

> **Per-prompt execution requirement:** The active prompt must be copied together with the complete **AI Engineer Execution Contract** above. That contract is normative and is part of every PMPT block; it mandates full implementation, exact validation commands/results, documentation, and a successful ID-prefixed Git commit before advancing.

### PMPT-0017
**Week/Milestone:** 4/4.1. **Title:** Package installer and release artifacts. **Dependencies:** PMPT-0013, PMPT-0014, PMPT-0015. **Effort:** 1.25 days. **Status:** Pending.

**Objective/output:** signed-or-development Windows EXE plus APK flow, installer/upgrade/repair/uninstall, firewall/ADB guidance, checksums/SBOM. **Files:** packaging/signing scripts/workflow/deployment docs. **Decision:** per-machine elevation only for camera registration; preserve pairings on upgrade, offer erase on uninstall. Test clean VM, upgrade/repair, missing ADB, firewall deny, invalid signature. **Commit:** `PMPT-0017: Package local Windows and Android release artifacts`. **Next:** PMPT-0018.

### PMPT-0018
**Week/Milestone:** 4/4.2. **Title:** Harden diagnostics and privacy. **Dependencies:** PMPT-0012, PMPT-0013, PMPT-0017. **Effort:** 0.75 day. **Status:** Pending.

**Objective/output:** redacted diagnostic export, retention, support/privacy guides. **Files:** exporter/redactor, config, tests, admin/user/known-issues/release docs. Test that bundle excludes media, keys, QR secrets, and default addresses; test rotation and errors. **Commit:** `PMPT-0018: Harden local diagnostics and privacy controls`. **Next:** PMPT-0019.

### PMPT-0019
**Week/Milestone:** 4/4.3. **Title:** Audit performance and compatibility. **Dependencies:** PMPT-0016, PMPT-0017. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** reproducible benchmark/endurance/compatibility evidence. **Files:** harness/results/matrix/docs. Test 30-minute 1080p30, 8-hour endurance, USB/Wi-Fi latency, CPU/GPU/RSS/battery/thermal, reconnect, frame drops across three phones/two PCs/current+previous OBS. Document deviations, do not lower claims silently. **Commit:** `PMPT-0019: Validate MVP performance and compatibility targets`. **Next:** PMPT-0020.

### PMPT-0020
**Week/Milestone:** 4/4.4. **Title:** Complete production readiness review. **Dependencies:** PMPT-0017, PMPT-0018, PMPT-0019. **Effort:** 1 day. **Status:** Pending.

**Objective/output:** threat audit, fuzz/SAST/dependency/license results, release checklist, risks/debt, rollback package, go/no-go. **Files:** reports, SBOM, manifest, release notes/docs. **Acceptance:** no untriaged critical/high blockers, signing/hashes verified, clean install/upgrade/rollback documented. **Commit:** `PMPT-0020: Complete production readiness review`.

## Master Checklist, Risks, Debt, Maintenance

- Every PMPT-0001–PMPT-0020 has a unique successful commit and completion record.
- Clean checkout builds Android, Host, virtual camera, OBS plugin, installer, tests, and docs.
- USB/Wi-Fi secure pairing/revoke/reconnect and declared consumer matrix pass.
- No backend, account, cloud, tracking, default telemetry, or persisted media exists.

Risks: virtual-camera compatibility (prototype early/VM matrix); one-month solo schedule (strict scope); vendor codec variance (capability negotiation/three-phone matrix); ADB friction (Wi-Fi fallback/guides); signing availability (acquire early; never misrepresent unsigned release). Technical debt: no virtual microphone, TCP LAN transport, English-only, x64-only; resolve only through ADR, security review, and dedicated test plan.

Use semantic versioning, signed tags, SBOM/checksum retention, monthly dependency review, quarterly compatibility review, severity-based issue triage, and user-reviewed redacted diagnostics. Preserve source, signed artifacts, SBOMs, checksums, and build inputs in two controlled locations. No server backup exists; recovery is reproducible build plus prior signed artifact, and endpoint loss is handled by local pairing revocation.

## Path-Level Implementation Map

This map is mandatory execution detail for the prompts above. Create a file only when its prompt is active; modify existing files instead of duplicating responsibilities. Keep one public type per Kotlin/C# source file where practical. Native headers expose contracts only; implementation stays in `.cpp`. Test paths mirror production paths. Every new production module receives a `README.md` that explains ownership, public contracts, threading, error model, and tests.

### Shared conventions

- Android package root: `com.camlink.local`. Kotlin files use `PascalCase.kt`; use cases end in `UseCase`, interfaces in `Port`, platform adapters in `Adapter`, immutable UI state in `State`.
- Windows namespace root: `CamLink`. C# projects use `CamLink.<Area>`; C++ targets use `camlink_<area>`. Native public APIs return `Expected<T, Error>` or mapped `HRESULT`; they never throw across ABI boundaries.
- Place common protocol DTOs in `shared/protocol/schema`; generated artifacts belong in ignored build folders, not source control. Use canonical CBOR test vectors in `shared/protocol/test-vectors`.
- Tests follow `<module>/src/test`, `<module>/src/androidTest`, `windows/<area>/tests`, and `tests/integration`. Fixtures contain synthetic data only.
- Each source change must update its module README and the relevant root document listed in the prompt; every new configuration key goes in `CONFIGURATION_REFERENCE.md`.

### PMPT-0001 exact file plan

Create `android/settings.gradle.kts`, `android/build.gradle.kts`, `android/gradle/libs.versions.toml`, `android/build-logic/`, `windows/CMakeLists.txt`, `windows/CMakePresets.json`, `windows/CamLink.sln`, `shared/protocol/README.md`, `tests/README.md`, `tools/README.md`, `.github/workflows/verify.yml`, `.github/workflows/release.yml`, `.editorconfig`, `.gitignore`, `LICENSE`, and `VERSION`. Modify `README.md`, `DEVELOPER_GUIDE.md`, `CODING_STANDARDS.md`, `GIT_WORKFLOW.md`, and `CHANGELOG.md`. CI order: checkout → restore cache → format → lint/analyzers → unit tests → build → scan → artifact/SBOM. Do not add release secrets or fake signing success.

### PMPT-0002 exact file plan

Create `android/app/src/main/java/com/camlink/local/App.kt`, `MainActivity.kt`, `di/AppModule.kt`, `navigation/AppNavGraph.kt`; domain contracts in `core-domain/src/main/.../{session,peer,diagnostics}`; DataStore implementation in `core-data`; Compose screens and ViewModels in `core-ui`; service boundary in `streaming`. Create mirrored JVM and instrumentation tests. Modify Android manifest for camera/microphone/foreground-service permissions and only required service declarations. Keep permission rationale in UI; never request permission on startup without user action.

### PMPT-0003 and PMPT-0004 exact file plan

Create `streaming/camera/CameraCapturePort.kt`, `CameraXCaptureAdapter.kt`, `CameraCapability.kt`, `CameraSelectorPolicy.kt`, `CameraControlPort.kt`, `Camera2ControlAdapter.kt`, `CaptureSessionState.kt`, and `CameraError.kt`. Add `core-domain/camera/{GetCapabilitiesUseCase,SelectCameraUseCase,UpdateCameraControlUseCase}.kt`. Add `core-ui/controls/CameraControlsViewModel.kt` and composables under `core-ui/controls`. Put fake adapters in test source only. Modify streaming DI bindings, profile documentation, `USER_MANUAL.md`, `API.md`, and `TESTING.md`.

### PMPT-0005 and PMPT-0006 exact file plan

Create `streaming/video/{VideoEncoderPort,MediaCodecVideoEncoder,VideoProfileNegotiator,EncodedVideoFrame,BoundedFrameQueue,KeyframeController}.kt` and `streaming/audio/{AudioCapturePort,AudioRecordAdapter,OpusEncoderPort,EncodedAudioFrame,AudioClock}.kt`. Add tests for every state transition and `shared/protocol/test-vectors/{h264,opus}` metadata fixtures. Modify `streaming/di/StreamingModule.kt`, `MEDIA_PIPELINES.md`, `PERFORMANCE.md`, `COMMUNICATION_PROTOCOL.md`, and configuration reference. Keep codec callbacks on dedicated dispatchers; queue policy must be measurable and documented.

### PMPT-0007 through PMPT-0010 exact file plan

Create schemas in `shared/protocol/schema/{Header,Message,Control,Pairing,Heartbeat,Error}.cddl` and reference implementation libraries in `android/core-protocol` and `windows/protocol`. Create `shared/protocol/test-vectors/{valid,invalid,replay,version}`. Create Android implementations under `streaming/{session,pairing,transport}` and Windows equivalents under `windows/host/src/{protocol,transport,pairing}`. Add `MdnsDiscoveryAdapter`, `TlsTransportAdapter`, `AdbTransportAdapter`, `HandshakeCoordinator`, `PeerKeyStore`, and `ReconnectPolicy`. Modify `API.md`, `COMMUNICATION_PROTOCOL.md`, `SECURITY.md`, `DATABASE.md`, `CONFIGURATION_REFERENCE.md`, `USER_MANUAL.md`, and `ADMIN_GUIDE.md`. ADB commands must use argument arrays/process APIs, explicit timeout, output redaction, and deterministic cleanup.

### PMPT-0011 and PMPT-0012 exact file plan

Create `windows/host/src/{main,session,decode,clock,ipc,diagnostics}` with `HostApplication`, `SessionManager`, `VideoDecoder`, `AudioDecoder`, `JitterBuffer`, `AvSynchronizer`, `NamedPipeServer`, and `MetricsRegistry`; public contracts go in `windows/host/include`. Add `windows/host/tests/{unit,contract,stress}` and test fixtures in `tests/integration/media`. Modify `windows/host/CMakeLists.txt`, `TECHNICAL_ARCHITECTURE.md`, `MODULE_DESIGN.md`, `MEDIA_PIPELINES.md`, `OBSERVABILITY.md`, and `PERFORMANCE.md`. Enforce pipe ACLs, handle ownership, queue limits, and a single serialized session state transition executor.

### PMPT-0013 exact file plan

Create `windows/desktop/src/CamLink.Desktop/{App,Views,ViewModels,Services,Models}` plus `CamLink.Desktop.Tests`. Add `HostIpcClient`, `PairingViewModel`, `SessionViewModel`, `DevicesViewModel`, `DiagnosticsViewModel`, and localized resource files. Modify the `.csproj` to enable nullable, analyzers, trimming policy, and code analysis. Modify `UI_UX.md`, `USER_MANUAL.md`, `DEVELOPER_GUIDE.md`, and `TESTING.md`. UI must not P/Invoke media/driver APIs directly.

### PMPT-0014 exact file plan

Create `windows/virtual-camera/{include,src,tests,package}`; source includes `VirtualCameraSource`, `MediaTypeNegotiator`, `HostFrameReader`, `DisconnectedFrameProvider`, and registration entry points. Create clean-VM scripts in `tools/packaging/test-vm`. Modify `windows/CMakeLists.txt`, installer manifests, `DRIVER_ARCHITECTURE.md`, `DEPLOYMENT.md`, and `KNOWN_ISSUES.md`. Keep source activation, media sample lifetime, and Host IPC isolation testable independently.

### PMPT-0015 exact file plan

Create `windows/obs-plugin/{src,include,tests,resources}` with `ObsModule`, `CamLinkSource`, `HostClient`, `VideoFrameUploader`, `AudioFrameOutput`, `PropertiesUi`, and resource strings. Modify CMake/OBS SDK discovery, `README.md`, `USER_MANUAL.md`, `TESTING.md`, and `KNOWN_ISSUES.md`. OBS callbacks must only use non-blocking frame handoff and must free graphics/audio resources in every source-destroy path.

### PMPT-0016 through PMPT-0020 exact file plan

Create `tests/integration/{pairing,usb,wifi,reconnect,consumer}` and `tests/performance/{latency,endurance,resource}`; store test orchestration in `tools/test`. Create packaging assets in `tools/packaging/{windows,android,signing}` and release metadata in `tools/release`. Add diagnostics code under Android `core-diagnostics` and Windows `host/src/diagnostics`; never copy logs into CI artifacts unless explicitly redacted. Modify `.github/workflows`, `DEPLOYMENT.md`, `TESTING.md`, `SECURITY.md`, `OBSERVABILITY.md`, `PERFORMANCE.md`, `RELEASE_NOTES_TEMPLATE.md`, `CHANGELOG.md`, `KNOWN_ISSUES.md`, `ROADMAP.md`, `ADMIN_GUIDE.md`, and `CONFIGURATION_REFERENCE.md` as each relevant prompt completes.

## Per-Prompt Delivery Template

At the beginning of every execution response, restate the active ID, title, dependencies verified, files to create, files to modify, and tests planned. At the end, emit:

```text
Prompt Completed
Prompt ID: PMPT-xxxx
Title: <title>
Files created: <paths>
Files modified: <paths>
Build: PASS
Formatting/Lint/Static analysis: PASS
Unit tests: PASS
Integration/performance tests: PASS or documented not-applicable reason
Documentation: <paths>
Git commit: PMPT-xxxx: <imperative title> (<hash>)
Next prompt: PMPT-yyyy
```

If any gate fails, replace `Prompt Completed` with `Prompt Blocked`, include exact command/result, preserve all changes without committing, and propose only the smallest safe remediation. Do not advance to the next prompt.
