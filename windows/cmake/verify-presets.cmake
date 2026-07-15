if(NOT DEFINED PRESET_FILE)
  message(FATAL_ERROR "PRESET_FILE is required")
endif()

file(READ "${PRESET_FILE}" presets_json)
string(JSON presets_version GET "${presets_json}" version)
if(NOT presets_version EQUAL 6)
  message(FATAL_ERROR "Expected CMake preset schema version 6")
endif()

string(JSON debug_name GET "${presets_json}" configurePresets 0 name)
if(NOT debug_name STREQUAL "windows-x64-debug")
  message(FATAL_ERROR "Missing windows-x64-debug configure preset")
endif()
