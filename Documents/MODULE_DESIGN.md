# Module Design

## Objective

Define independently testable production modules and their contracts.

| Module | Responsibilities | Dependencies | Primary interfaces |
|---|---|---|---|
| Android Capture | camera selection, controls, lifecycle | CameraX/Camera2 | `CapturePort`, `CameraCapabilities` |
| Android Encode/Audio | H.264/Opus, timestamps | MediaCodec, AudioRecord | `EncodedFrameSource` |
| Session/Trust | pairing, keys, lifecycle | Keystore/DPAPI, protocol | `SessionController`, `PeerRepository` |
| Transport | mDNS, ADB/TLS stream, heartbeats | TLS, ADB | `Transport`, `DiscoveryPort` |
| Windows Host | receive, decode, A/V clock, IPC | Media Foundation | `FramePublisher`, `HostApi` |
| Virtual Camera | MF registration, frame sample delivery | Windows MF | `IVirtualCameraSource` |
| OBS Plugin | source lifecycle, settings, audio/video | OBS SDK, Host IPC | `obs_source_info` adapter |
| Desktop UI | pairing/settings/diagnostics | WinUI, HostApi | ViewModels and commands |

Modules communicate through versioned DTOs and contracts in `shared/`; they may not import each other’s UI or platform implementation. Each module owns error mapping, cancellation, and bounded-resource behavior. Complexity is highest in Windows Host/virtual camera and medium-high in Android media. Mock all ports in domain tests; use contract tests for each implementation.
