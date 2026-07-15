# Deployment and Release

## Objective

Ship reproducible, signed Android and Windows artifacts with safe rollback.

## Environments

Development uses debug keys and local devices. Staging uses isolated signing identities, release candidates, and a representative hardware lab. Production is GitHub Releases with immutable tag, checksums, SBOM, signed Android APK, signed Windows `EXE`, and OBS plugin package. There is no runtime server or Docker deployment.

## Installer and updates

The Windows bootstrapper supports per-machine installation, elevates only for virtual camera registration, verifies Authenticode signatures, installs the Host/desktop app/plugin, and leaves user pairing data intact on upgrade. Updates are user-approved by default; automatic download is opt-in after v1 security review. Roll back by reinstalling the previous signed release; migrations must remain backward-compatible for one minor release. Uninstall must offer removal of pairings/logs.

## Release checklist

- CI green, signed commits/tags, dependency and license scan clear.
- Windows driver/camera package passes target-version compatibility tests.
- APK and EXE signatures, hashes, SBOM, release notes, and rollback artifact published.
- Manual smoke test on USB, Wi-Fi, OBS, Zoom/Teams/Meet/Discord where available.
- Security, privacy, accessibility, and support-owner approval recorded.

Code-signing certificates are held by a managed secret provider with least privilege, short-lived CI credentials, and audit logs. MSI/MSIX were considered; signed EXE is selected for ADB dependency and virtual-camera installation flexibility. See [CONFIGURATION_REFERENCE.md](CONFIGURATION_REFERENCE.md).
