# Coding Standards

Use Kotlin official style, `ktfmt`, null-safe APIs, structured concurrency, immutable state, and explicit dispatcher injection. Use C++20 with clang-format, RAII, `gsl::not_null` or equivalent ownership clarity, no raw owning pointers, checked arithmetic, and no exceptions across ABI boundaries. Use C# nullable reference types, analyzers, async cancellation, and MVVM commands free of blocking work.

Keep functions focused, names descriptive, dependencies inward, and public contracts documented. Require input validation at every boundary, correlation IDs in errors, tests for bug fixes, and no secrets/media in logs. Prefer composition over inheritance; prohibit unbounded queues, global mutable state, and UI-to-platform direct calls. Formatting and static analysis are CI-required.
