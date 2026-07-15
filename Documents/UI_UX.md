# UI/UX Design

## Objective

Make first pairing and daily streaming obvious, privacy legible, and accessible.

```text
Windows: [Device status] [Pair device] [USB | Wi-Fi] [Profile] [Start/Stop]
         [Connection quality] [Camera controls] [Diagnostics] [Trusted devices]
Android: [Preview] [Camera switch] [Profile] [Start] [Battery/quality]
         [Pair PC] [Trusted PCs] [Privacy and permissions]
```

Navigation is shallow: Home, Pair, Controls, Trusted Devices, Diagnostics/Settings. Pairing explains local-only operation, shows device identity on both endpoints, and requires explicit confirmation. Streaming state uses text plus color: stable, adapting, reconnecting, disconnected; color is never the sole indicator. Android uses Material 3; Windows uses WinUI 3 with system light/dark themes. All interactive elements have labels, visible focus, keyboard access on Windows, scalable text, contrast meeting WCAG 2.2 AA, and screen-reader-tested state announcements.

English launches first. Localization infrastructure uses resource keys from day one. Avoid modal dialogs during streaming, permission jargon without explanation, hidden camera capability failures, and controls that imply unsupported hardware.
