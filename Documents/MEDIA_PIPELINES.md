# Video and Audio Pipelines

## Video

`CameraX lifecycle → Camera2 capability/control interop → MediaCodec H.264 hardware encoder → framed transport → Host jitter buffer → Media Foundation hardware decode → frame clock → Virtual Camera and OBS shared-memory sinks`.

Use codec surface input, keyframes every 1–2 seconds, SPS/PPS repeated on every IDR, monotonic PTS, limited in-flight frames, and host-led adaptation. Render neither preview nor virtual-camera frames on the UI thread. Color space starts with SDR BT.709; HDR is post-v1 because tone mapping and consumer compatibility need dedicated validation. Screenshot/recording are post-v1 to preserve latency and privacy guarantees.

## Audio

`AudioRecord → optional platform AEC/NS/AGC when supported → Opus 48 kHz mono → transport → Host jitter buffer → OBS audio frames`. Audio is not presented as a Windows virtual microphone in v1. The Host aligns audio/video from monotonic capture timestamps, targets <20 ms drift, and corrects via bounded resampling/drop-dup policy rather than changing wall-clock time. Audio feedback processing is explicitly surfaced because it can affect quality.

## Trade-offs

Platform codecs minimize package size, battery, and CVE surface but require a capability matrix. Software fallback is not a v1 promise. Opus is selected for low latency/robustness; AAC was rejected because OBS integration does not require broad standalone playback compatibility. Common pitfalls are B-frame latency, timestamp mixing, assuming hardware decode, and allowing adaptation to oscillate.
