cmake_minimum_required(VERSION 3.25)

project(CamLinkNative VERSION 0.1.0 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_executable(camlink-host native/src/main.cpp)
target_compile_features(camlink-host PRIVATE cxx_std_20)

include(CTest)
if(BUILD_TESTING)
  add_test(
    NAME configuration-parsing
    COMMAND ${CMAKE_COMMAND}
      -DPRESET_FILE=${CMAKE_CURRENT_SOURCE_DIR}/CMakePresets.json
      -P ${CMAKE_CURRENT_SOURCE_DIR}/cmake/verify-presets.cmake)
endif()
