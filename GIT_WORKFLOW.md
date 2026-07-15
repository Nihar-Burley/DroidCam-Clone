# Git Workflow

Use short-lived `feat/`, `fix/`, `docs/`, and `chore/` branches and conventional commits. Protect `main`; PRs state scope, tests, and security/privacy impact. Tag releases as `vMAJOR.MINOR.PATCH`, exactly matching `VERSION`.

Never commit build output, IDE state, signing credentials, certificates, diagnostics, or captures. Protocol, driver, signing, and trust-model changes require an ADR and release-note entry.
