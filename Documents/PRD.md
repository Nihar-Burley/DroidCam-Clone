# Product Requirements Document

## Objective

Deliver a free, local-first Android phone webcam for Windows that is dependable in OBS and common conferencing applications without accounts or cloud infrastructure.

## Business goals and success measures

The product targets creators, remote workers, educators, and privacy-conscious users. A successful public MVP installs cleanly, pairs in under 60 seconds, sustains 1080p30 on the baseline phone and PC, has median USB latency under 100 ms and Wi-Fi latency under 180 ms, and reaches at least 90% successful first-session completion in pilot telemetry-free usability studies.

## Personas

| Persona | Need | Outcome |
|---|---|---|
| Streamer | Stable OBS source and phone controls | Switch camera/settings without disrupting a scene |
| Remote worker | Better meeting camera with no account | Select CamLink as a camera after one pairing |
| Educator | Simple repeatable setup | Reconnect after restarting either device |

## Scope

**In:** Android 10+, Windows 10 22H2/11 x64; ADB USB; same-LAN Wi-Fi; H.264, Opus-to-OBS; virtual camera; mDNS/manual IP; QR trust pairing; front/rear camera; 480p–1080p, 15/30/60; basic focus/exposure/torch/zoom where supported; reconnect and diagnostics.

**Out:** accounts, cloud relay, payments, analytics, internet traversal, virtual microphone, 4K, recording, screen share, multi-phone production, AI effects, iOS/macOS/Linux, and Windows ARM64.

## Functional requirements and acceptance criteria

| ID | Requirement | Acceptance criterion |
|---|---|---|
| FR-01 | Pair local phone and PC | QR flow requires confirmation on both devices; an untrusted peer cannot stream |
| FR-02 | Stream video on USB/Wi-Fi | 1080p30 works on the reference matrix; quality adapts before disconnecting |
| FR-03 | Expose camera | CamLink Virtual Camera appears after signed per-machine install and produces a placeholder when idle |
| FR-04 | Integrate OBS | Native plugin adds a source, exposes connection/status/settings, and receives synchronized audio |
| FR-05 | Recover | Connection retries with exponential backoff for 10 minutes and resumes trusted sessions |
| FR-06 | Preserve privacy | No login, outbound internet dependency, media persistence, or enabled-by-default telemetry |

## Non-functional requirements

Security uses authenticated pairing, TLS 1.3 on Wi-Fi, replay-resistant control messages, per-device keys, least privilege, and local-only logs. The app must remain responsive during device loss, bound memory through fixed media queues, and degrade deterministically. Accessibility target is WCAG 2.2 AA; English is the launch language.

## User stories

- As a creator, I pair once and select my phone as an OBS source on later launches.
- As a remote worker, I select a virtual camera in a meeting app without exposing video to a third party.
- As a user on weak Wi-Fi, I see connection quality and automatic quality reduction rather than unexplained failure.

## Decisions, alternatives, and delivery

ADB USB is selected over custom USB for solo-MVP feasibility; it trades setup friction for reliable transport. Native OBS integration is selected over WebSocket control because it can provide media and source lifecycle integration. Virtual microphone is deferred: a signed audio driver adds certification and support risk. Complexity is high; estimated effort is four weeks: protocol/capture (1.5), Windows media/OBS (1.5), integration, QA, packaging (1). Dependencies: AndroidX CameraX, MediaCodec, Media Foundation, OBS SDK, and platform signing assets.
