# Shared protocol

This directory is the canonical home for versioned transport schemas, fixtures, and compatibility vectors shared by Android and Windows.

- Add a schema and a positive/negative fixture together.
- Keep wire changes backward compatible within a major version.
- Update `VERSION` and the protocol documentation when a release changes compatibility.
- Never include keys, captured media, or production diagnostics in fixtures.
