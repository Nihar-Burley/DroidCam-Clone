# ADR-002: One framed protocol over TLS Wi-Fi and ADB USB

## Status

Accepted.

## Decision

Use one versioned framed protocol with H.264/Opus media. Wi-Fi runs over mutually authenticated TLS 1.3 TCP; USB uses ADB tunneling plus application peer authentication. Discovery is mDNS with manual IP fallback.

## Alternatives and consequences

WebRTC and UDP/RTP provide benefits for internet use but add unnecessary MVP complexity. Custom USB avoids ADB setup but exceeds the solo schedule. The downside is TCP head-of-line risk on lossy Wi-Fi, mitigated by adaptive encoding and narrow bounded buffers.
