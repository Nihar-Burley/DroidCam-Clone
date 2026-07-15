# Logging, Diagnostics, and Maintenance

## Objective

Enable support and recovery without collecting user media or telemetry.

Structured local logs use levels `ERROR`, `WARN`, `INFO`, and opt-in `DEBUG`; every session event includes correlation ID, component, state, and non-sensitive performance fields. Never log keys, QR data, raw addresses by default, media, or user-generated camera content. Rotate at 50 MiB/14 days. A user-triggered diagnostic bundle redacts secrets and includes versions, capabilities, state transitions, counters, and installer logs.

Monitor only local health indicators: capture/encode/decode queue depth, latency estimate, frame rate, dropped frames, reconnect count, battery, and thermal warnings. There is no central monitoring. Recovery procedures: restart the Host after crash, offer re-pair after trust failure, repair install after virtual-camera registration failure, and preserve no media. Support triage prioritizes install/security defects, then broken streaming, then quality issues. See [ADMIN_GUIDE.md](ADMIN_GUIDE.md).
