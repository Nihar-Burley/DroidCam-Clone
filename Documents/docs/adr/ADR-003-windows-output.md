# ADR-003: Media Foundation virtual camera and OBS native plugin

## Status

Accepted.

## Decision

Use a C++ Media Foundation virtual-camera component fed by a separate native Host, plus a native OBS plugin for video and audio. Use WinUI 3/.NET 10 only for the companion UI and orchestration.

## Consequences

This favors modern Windows integration and isolates fragile media work from UI. DirectShow is a fallback only if tested consumers demand it. A virtual audio driver is deferred because its signing, stability, and support cost violates the MVP risk budget.
