# Coding Standards

Kotlin uses official style and `ktfmt`; C++ uses C++20 and `clang-format`; C# uses nullable reference types and SDK analyzers. CI treats compiler warnings as errors for .NET and runs formatting, CMake configuration tests, SAST, secret, dependency, and license checks. Configure the optional FOSSA token to enforce the license gate.

Keep boundaries explicit, validate external input, use cancellable bounded work, and do not log secrets, pairing data, or media. Pin all build tools and action versions; do not add runtime Docker or cloud dependencies without an ADR.
