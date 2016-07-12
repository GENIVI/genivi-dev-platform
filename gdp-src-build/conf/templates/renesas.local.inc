include local.inc

# Define the name of multimedia, dtv, graphic test, multimedia-test for bbmask
BB_MULTIMEDIA_KERNEL_MODULE = "fdpm-kernel-module|mmngr-kernel-module|\
                               mmngrbuf-kernel-module|s3ctl-kernel-module|\
							   uvcs-kernel-module|vspm-kernel-module"
BB_MULTIMEDIA_USER_MODULE = "fdpm-user-module|mmngr-user-module|\
                             mmngrbuf-user-module|omx-user-module|\
							 s3ctl-user-module|vspm-user-module|libmemcpy"
BB_MULTIMEDIA_TEST_MODULE = "fdpm-tp-user-module|mmngr-tp-user-module|\
                             mmngrbuf-tp-user-module|s3ctl-tp-user-module|\
							 vspm-tp-user-module"
BB_GST_PLUGINS = "meta-renesas/meta-rcar-gen2/recipes-multimedia/gstreamer"
MULTIMEDIA_BB = "${BB_MULTIMEDIA_KERNEL_MODULE}|${BB_MULTIMEDIA_USER_MODULE}|\
                 ${BB_MULTIMEDIA_TEST_MODULE}|${BB_GST_PLUGINS}"

# Enable Multimedia standard pkg
MACHINE_FEATURES_append = " multimedia"

# UnMask the multimedia kernel modules
#BBMASK = "${BB_MULTIMEDIA_KERNEL_MODULE}"

# UnMask the multimedia user modules
#BBMASK .= "|${BB_MULTIMEDIA_USER_MODULE}"

# Include static library in SDK
SDKIMAGE_FEATURES_append = " staticdev-pkgs"

# Mask the DTV pkgs
#MACHINE_FEATURES_append = " dtv"
BBMASK = "dtv-module|ssp-module|scu-module"

# Enable graphic pkgs
#BBMASK .= "|gles-kernel-module|gles-user-module"

# Mask the multimedia-test
BBMASK .= "|${BB_MULTIMEDIA_TEST_MODULE}"

# Mask the graphic test
BBMASK .= "|gles-test-module"

#DISTRO_FEATURES_append = " mm-test"
#DISTRO_FEATURES_append = " gles-test"

# Enable Gfx Pkgs
MACHINE_FEATURES_append = " sgx"
MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

# for Wayland/Weston

PREFERRED_PROVIDER_virtual/libgles1 = ""
PREFERRED_PROVIDER_virtual/libgles2 = "gles-user-module"
PREFERRED_PROVIDER_virtual/egl = "libegl"
PREFERRED_PROVIDER_virtual/libgl = ""
PREFERRED_PROVIDER_virtual/mesa = ""
PREFERRED_PROVIDER_libgbm = "libgbm"
PREFERRED_PROVIDER_libgbm-dev = "libgbm"
