# Configuration Reference

## Runtime settings

| Setting | Default | Range/meaning |
|---|---|---|
| `video.profile` | `1080p30` | capability-clamped 480p–1080p, 15/30/60 FPS |
| `video.bitrateKbps` | `8000` | 1000–20000, adaptive on Wi-Fi |
| `transport.preference` | `usb` | `usb`, `wifi`, `auto` |
| `reconnect.maxMinutes` | `10` | 1–30 |
| `logs.retentionDays` | `14` | 1–30; size cap remains 50 MiB |
| `theme` | `system` | `light`, `dark`, `system` |

Capability checks override unsupported settings rather than failing late. Settings are per-device only when that avoids surprising behavior; stream profile selection is persisted per paired phone.

## Build and CI secrets

`ANDROID_KEYSTORE_BASE64`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD`, `WINDOWS_SIGN_CERT_THUMBPRINT`, `WINDOWS_SIGN_KEY_REFERENCE`, and `GITHUB_TOKEN` are CI-secret references only. Do not create local `.env` files containing production signing material. CI injects secrets at signing time; forks must build unsigned artifacts.

## Network/firewall

The Host advertises only on private network profiles. The installer requests an inbound private-network firewall rule for the negotiated Host port; user may disable discovery and enter a peer IP manually. Internet/public-network listening is prohibited in v1.
