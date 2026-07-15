# Git Workflow

Use protected `main`, short-lived `feat/`, `fix/`, `docs/`, and `chore/` branches, and pull requests with one owner approval when collaborators exist. Use Conventional Commits (`feat:`, `fix:`, `docs:`, `build:`), rebase/update before merge, and squash merge unless preserving a release branch. Tags are signed `vMAJOR.MINOR.PATCH` releases. Never rewrite shared history or commit secrets, binaries, test captures, pairing stores, or certificates.

Every PR states scope, architecture impact, tests run, and security/privacy effect. Protocol, driver, signing, or data migration changes require an ADR and release-note entry.
