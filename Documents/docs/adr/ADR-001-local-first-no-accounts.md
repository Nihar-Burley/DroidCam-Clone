# ADR-001: Local-first operation without accounts

## Status

Accepted.

## Context and decision

The product must be free, have no login, and incur no required operating cost. v1 therefore has no backend, cloud relay, analytics, licensing service, or remote-access path. Trust is established directly by QR-confirmed pairing and stored locally.

## Consequences

Privacy, availability, and cost improve; cross-network access, device sync, account recovery, and centralized crash analytics are unavailable. Any future remote feature needs a replacement identity and operations architecture.
