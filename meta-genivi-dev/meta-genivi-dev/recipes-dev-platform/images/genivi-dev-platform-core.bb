# Copyright (C) 2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GENIVI Development Platform core image (minimal)"

PV = "13.0"

IMAGE_FEATURES_append = " ssh-server-openssh tools-debug"

require recipes-yocto-ivi/images/ivi-image.inc

# Remove qemu build - users more typically run QEMU installed by their
# distro
EXTRA_IMAGEDEPENDS_remove = "qemu-native qemu-helper-native"

# We want to remove certain distro (graphics) features when a user runs:
#
#     $ bitbake genivi-dev-platform-core
#
# We can not do this unconditionally since 'genivi-dev-platform-core' is
# included in other image types where graphics is of interest and hence
# this workaround.
#
DISTRO_FEATURES_remove = "${@bb.utils.contains('PN', \
    'genivi-dev-platform-core', 'wayland pulseaudio opengl', '', d)}"

IMAGE_INSTALL_append = " \
    packagegroup-gdp-core \
    packagegroup-gdp-gps \
    packagegroup-gdp-vsi \
"

# Required by acceptance tests to be able to set QEMU IP address using
# the "ip=" kernel argument.
IMAGE_INSTALL_append_qemuall = " \
    connman-conf \
"

# CUSTOM_IMAGE_FSTYPE should be defined in each machine-specific local.conf template
IMAGE_FSTYPES = "${CUSTOM_IMAGE_FSTYPE}"
