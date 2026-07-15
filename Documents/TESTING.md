# Testing Strategy

## Objective

Prove correctness, compatibility, latency, resilience, and security for the public MVP.

| Layer | Tests | Target |
|---|---|---|
| Android domain/UI | unit, coroutine/Flow, accessibility | ≥80% domain coverage |
| Protocol | golden vectors, malformed frames, version negotiation | 100% message/error cases |
| Native Host | unit, decoder integration, leak checks | ASan where supported |
| Virtual camera/OBS | install, source lifecycle, consumer compatibility | all supported Windows builds |
| End-to-end | USB/Wi-Fi pairing, reconnect, A/V sync | reference device matrix |
| Security | fuzzing, SAST, dependency/license scan | no untriaged high severity |

Reference matrix: three Android phones (baseline Snapdragon 720G Android 10; current Qualcomm Android 14; current MediaTek/Samsung Android 14+), two Windows x64 systems (integrated and discrete GPU), and current plus previous OBS major versions. Pilot beta users may test only opt-in release candidates and must receive diagnostic/privacy guidance.

Performance gates: USB p50/p95 latency ≤100/150 ms, Wi-Fi ≤180/250 ms in a clean 5 GHz LAN; 1080p30 for 30 minutes without crash; bounded memory/no unbounded queue growth; reconnect within 30 seconds after an ordinary Wi-Fi interruption. Stress tests run 8-hour sessions, repeated pair/revoke, hostile control traffic, device rotation/backgrounding, sleep/resume, network switching, and installer repair/uninstall.

Driver testing is mandatory before each release: clean VM install/upgrade/uninstall, virtual-camera enumeration in OBS/Zoom/Teams/Chrome, and no crash/hang under consumer restart. External pen testing is recommended before any remote-access roadmap work.
