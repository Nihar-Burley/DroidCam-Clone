# Administrator Guide

## Objective

Provide safe managed installation for small organizations without introducing a hosted service.

Deploy the signed EXE through the organization’s endpoint-management system with per-machine installation and private-network firewall policy. Provision Android APKs through managed distribution where permitted. ADB USB requires an explicit organization decision: it exposes debugging capabilities and should be permitted only for trusted, managed endpoints. Wi-Fi is restricted to private LANs; disable discovery by policy and use manual pairing if multicast is blocked.

Verify Authenticode signatures and release checksums before deployment. Do not export user pairings or diagnostics centrally by default. Support staff should request a user-reviewed diagnostic bundle, record product version and hardware, and advise revocation before device reassignment. Driver/camera deployment changes require staged rollout, pilot validation, and documented rollback to the prior signed package.
