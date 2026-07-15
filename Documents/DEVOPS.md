# DevOps and Release Engineering

GitHub Actions builds Android, .NET, native Host, virtual camera, and OBS plugin on protected branches. Pull requests run formatting, lint, unit/contract tests, SAST, dependency/license scan, and build reproducibility checks. Nightly hardware jobs run integration and performance tests; release tags require manual approval and use isolated signing credentials.

Use trunk-based development with short-lived branches, conventional commits, semantic versioning, SBOM generation, and signed annotated tags. Artifacts are retained with hashes and provenance. Docker is optional for deterministic lint/test tooling only; runtime has no containers. Release pipeline: candidate → pilot hardware matrix → security/privacy gate → sign/package → GitHub Release → post-release smoke/issue triage. Rollback is a published prior signed package and compatible migration path.
