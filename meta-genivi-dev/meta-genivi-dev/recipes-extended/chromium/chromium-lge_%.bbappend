FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# FIXME: workaround for https://at.projects.genivi.org/jira/browse/LM-2
CHROMIUM_EXTRA_ARGS_append = " --window-size=1728,1080"

# Required to run Chromium as root. Not recommended for production.
CHROMIUM_EXTRA_ARGS_append = " --no-sandbox"

# Workarounds for R-Car gen 3 and Dragonboard
# enable size optimization for binaries, to prevent R_AARCH64_ABS32 relocation overflow

# Remove optimization flags and force optimization for size

TARGET_CFLAGS_remove_rcar-gen3 = "-O2"
TARGET_CFLAGS_remove_rcar-gen3 = "-fpie"
TARGET_CXXFLAGS_remove_rcar-gen3 = "-O2"
TARGET_CXXFLAGS_remove_rcar-gen3 = "-fpie"
TARGET_CFLAGS_append_rcar-gen3 = " -Os"
TARGET_CXXFLAGS_append_rcar-gen3 = " -Os"

TARGET_CFLAGS_remove_dragonboard-410c = "-O2"
TARGET_CFLAGS_remove_dragonboard-410c = "-fpie"
TARGET_CXXFLAGS_remove_dragonboard-410c = "-O2"
TARGET_CXXFLAGS_remove_dragonboard-410c = "-fpie"
TARGET_CFLAGS_append_dragonboard-410c = " -Os"
TARGET_CXXFLAGS_append_dragonboard-410c = " -Os"

# This patch ensures that Yocto-defined flags get priority over Chromium's

SRC_URI_append_rcar-gen3 = "\
    file://0001-Rotate-gcc-toolchain-s-build-flags.patch \
"
SRC_URI_append_dragonboard-410c = "\
    file://0001-Rotate-gcc-toolchain-s-build-flags.patch \
"
