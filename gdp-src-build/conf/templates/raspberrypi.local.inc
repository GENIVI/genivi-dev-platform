include local.inc

# vc4grahpics do not use GPU_MEM, instead it uses the CMA memory, allocating
# a high number of GPU_MEM does seem to eat memory from CMA causing OOM errors.
#
# Ultimatly we would like to set this to 0, but it does seem to need a small
# amount otherwise the system will not boot. Fails at first stage already,
# loading the Linux kernel.
GPU_MEM = "16"

MACHINE_FEATURES_append = " vc4graphics"

MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"
