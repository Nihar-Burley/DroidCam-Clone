# Security Architecture

## Objective

Protect local media, prevent unauthorized device use, and minimize data collection.

## Threat model and controls

| Threat | Control |
|---|---|
| LAN attacker discovers service | mDNS is unauthenticated; streaming requires QR-derived mutual authentication and TLS 1.3 |
| QR interception/replay | short expiry, single-use 256-bit secret, dual confirmation, nonce/sequence checks |
| Stolen PC/phone | OS-protected private keys; revoke from either endpoint; no stored media |
| Malicious frame/control input | size caps, schema validation, decoder sandboxing boundaries, fuzzing, rate limits |
| Supply-chain compromise | pinned dependencies, SBOM, signed releases, provenance and review gates |
| Diagnostic leakage | redaction, local retention cap, user-reviewed export |

## Requirements

Apply OWASP MASVS principles to Android storage/networking and OWASP ASVS principles to desktop IPC/input handling. Validate all lengths, enum values, codec configuration, and image dimensions before allocation. Use CSPRNGs; zero sensitive transient buffers where practical; rotate ephemeral session keys every connection and trusted keys on re-pair. Never log secrets, QR payloads, media, IP addresses unless explicitly included in a user-exported diagnostic bundle.

## Decisions and limitations

Certificate pinning binds to the QR bootstrapped ephemeral identity, avoiding public PKI and supporting offline pairing. No remote access reduces attack surface; adding it requires a new threat model, account recovery, relay security, abuse controls, and independent security review. Windows driver signing is mandatory for public release; a kernel audio driver is intentionally deferred. Before release, complete dependency scanning, SAST, mobile/desktop penetration tests, protocol fuzzing, and a privacy review.
