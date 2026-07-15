# Tests

Tests are organized by ownership: Android unit tests stay with Android modules, native tests use CTest, and cross-platform protocol vectors live in `shared/protocol/`.

Run the scaffold checks from the repository root:

```powershell
cmake --preset windows-x64-debug -S windows
cmake --build windows/out/build/windows-x64-debug
ctest --test-dir windows/out/build/windows-x64-debug --output-on-failure
dotnet build windows/CamLink.sln --configuration Release
```
