# Performance Plan

## Objectives and budgets

USB p50/p95 end-to-end latency is ≤100/150 ms; Wi-Fi is ≤180/250 ms in the stated test environment. Baseline target is 1080p30 H.264 at 8 Mbps, adaptable 1–20 Mbps; 1080p60 is capability-dependent. Fixed queue budgets cap capture+encode at 3 frames and host jitter at 60–120 ms. Memory is bounded by preallocated frame pools; each component reports pressure.

## Optimizations

Prefer hardware codec surfaces and Media Foundation hardware decode, avoid RGB conversion/copies, use direct buffer ownership, batch metrics, and suspend preview quality before transport quality. Adapt bitrate, then FPS, then resolution with hysteresis. Android holds a foreground service only while streaming, releases camera/mic promptly, observes thermal state, and warns before damaging thermal modes. Battery target is a 60-minute baseline session without severe throttling.

Benchmark every reference device after codec, transport, or driver change. Capture median/p95 latency, CPU/GPU, RSS, battery drain, temperature, dropped frames, A/V drift, and recovery time. Do not optimize based solely on a flagship phone or a loopback network.
