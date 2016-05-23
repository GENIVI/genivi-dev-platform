# Standard GDP configuration defaults, inherited by all targets

DISTRO ?= "poky-ivi-systemd"
USE_GSTREAMER_1_00 ?= "1"
PREFERRED_VERSION_weston ?= "1.9.0"
PREFERRED_VERSION_wayland-ivi-extension ?= "1.9.1"
PACKAGE_CLASSES ?= "package_rpm package_ipk"
EXTRA_IMAGE_FEATURES ?= "debug-tweaks"
USER_CLASSES ?= "buildstats image-mklibs image-prelink"
PATCHRESOLVE ?= "noop"
LICENSE_FLAGS_WHITELIST  += "commercial"

#INCOMPATIBLE_LICENSE ?= "GPLv3"

# Flag for Faytech Touch Monitor support, '1' will enable kernel driver patch
#USE_FAYTECH_MONITOR = "1"
