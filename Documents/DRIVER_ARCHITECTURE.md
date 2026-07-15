# Windows Driver and Integration Architecture

## Objective

Expose a reliable virtual camera while avoiding unnecessary kernel-mode code.

The v1 camera is a native Media Foundation virtual-camera component registered by the signed Windows installer. It is deliberately tiny: process-safe connection to the Host, media-type negotiation, frame pull/sample delivery, disconnected placeholder, and telemetry-free diagnostics. The Host owns decode and never executes inside an arbitrary consumer process. The component must support the tested formats required by target applications and fail closed on invalid IPC.

Kernel-mode virtual audio is deferred to v1.1. It would require a signed driver package, HLK/compatibility work, elevated install/removal handling, broader crash impact, and a support plan. OBS gets Opus-decoded audio through the plugin in v1. DirectShow was considered but rejected as the primary architecture; add it only with evidence from compatibility testing.

Release requirements: EV/appropriate Windows code-signing process, reproducible package build, clean-VM install/upgrade/uninstall, consumer app enumeration tests, and no unsigned production binaries. Driver signing, installer permissions, and support burden are high complexity and must not be bypassed.
