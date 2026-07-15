# ADR-004: Platform hardware codecs first

## Status

Accepted.

## Decision

Use Android MediaCodec H.264 encoding and Media Foundation hardware decode where available; use Opus for audio to OBS. Do not include FFmpeg in v1.

## Consequences

Battery use, package size, and security surface improve. Capability variance is handled through a device matrix and explicit profile negotiation; unsupported devices degrade or are not promised rather than silently falling back to expensive software encoding.
