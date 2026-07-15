# Local API and IPC

## Objective

Specify the authenticated phone-to-host protocol and the local Windows IPC boundary. There is no public internet API.

## Transport API

Wi-Fi uses TLS 1.3 TCP on a negotiated port advertised by mDNS; USB tunnels the identical byte stream over ADB. Every application message has a 16-byte fixed header: `magic(4) | protocolVersion(2) | type(1) | flags(1) | sequence(4) | payloadLength(4)`, followed by canonical CBOR payload. Maximum control payload is 64 KiB; media messages are capped at 2 MiB.

| Message | Direction | Auth | Purpose |
|---|---|---|---|
| `HELLO` | either | TLS + pairing proof | negotiate versions/capabilities |
| `PAIR_REQUEST` | phone→host | QR secret | establish trusted device |
| `START_SESSION` | host→phone | trusted key | profile and stream start |
| `VIDEO_FRAME` | phone→host | session | H.264 access unit with PTS |
| `AUDIO_FRAME` | phone→host | session | Opus packet with sample timestamp |
| `CONTROL` | both | session | camera and adaptation commands |
| `HEARTBEAT` | both | session | liveness/quality |
| `ERROR` | both | session | machine-readable failure |

Example `START_SESSION` payload:

```json
{"requestId":"c1e9","profile":{"width":1920,"height":1080,"fps":30,"videoBitrateKbps":8000,"audio":true},"sessionNonce":"base64url"}
```

Success: `{"requestId":"c1e9","status":"accepted","streamId":7}`. Errors use `UNAUTHENTICATED`, `UNSUPPORTED_VERSION`, `INVALID_ARGUMENT`, `DEVICE_BUSY`, `CAPABILITY_UNAVAILABLE`, `RATE_LIMITED`, and `INTERNAL`; never place secrets in error text. Control requests are limited to 20/s per peer; pairing to 5/hour per host.

## Windows local IPC

The WinUI app and OBS plugin access the Host through per-user named pipes with an ACL limited to the interactive user. Calls are `GetStatus`, `ListPeers`, `BeginPairing`, `Start`, `Stop`, `SetProfile`, `SetCameraControl`, and `ExportDiagnostics`. Each request carries a schema version and correlation ID. OBS receives shared-memory frame handles only after pipe authentication.

## Compatibility

Unknown optional fields must be ignored; unknown required message types fail safely. A major protocol mismatch rejects the connection; minor versions negotiate the lower common capability set. Full framing, state, replay protection, and reconnection rules are in [COMMUNICATION_PROTOCOL.md](COMMUNICATION_PROTOCOL.md).
