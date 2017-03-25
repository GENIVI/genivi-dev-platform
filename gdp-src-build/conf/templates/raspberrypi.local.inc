include local.inc

GPU_MEM = "128"

MACHINE_FEATURES_append = " vc4graphics"

MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"
