FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "mesa"
DEPENDS_append_rcar-gen3 = " libgbm"

SRC_URI += "\
    file://0001-seccomp-bpf-Allow-MADV_FREE-in-madvise-2.patch \
    file://google-chrome.desktop \
"
SRC_URI_append_rcar-gen3 = " file://fix-egl-version.patch"

# FIXME: workaround for https://at.projects.genivi.org/jira/browse/LM-2
CHROMIUM_EXTRA_ARGS_append = " --window-size=1728,1080"

do_install_append() {
    install -d ${D}${datadir}/applications/
    install -m 0444 ${WORKDIR}/google-chrome.desktop ${D}${datadir}/applications/

    for size in 22 24 48 64 128 256; do
        install -Dm 0644 ${S}/chrome/app/theme/chromium/product_logo_$size.png \
            ${D}${bindir}/${BPN}/product_logo_$size.png
    done
}

# Raspberry Pi workarounds

COMPATIBLE_MACHINE_armv7ve = "(.*)"

# Renesas workarounds

COMPATIBLE_MACHINE_rcar-gen3 = "(.*)"

# Apply same TUNE_FEATURES as in an armv7a build
ARMFPABI_armv7ve = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'arm_float_abi=hard', 'arm_float_abi=softfp', d)}"

# Remove cortexa7 optimization that conflicts with Chromium's hardcoded ARM flags
TUNE_FEATURES_remove_armv7ve = "cortexa7"

# Multi-seat implementation

SRC_URI_remove = " git://github.com/01org/ozone-wayland.git;destsuffix=${OZONE_WAYLAND_GIT_DESTSUFFIX};branch=${OZONE_WAYLAND_GIT_B\
RANCH};rev=${OZONE_WAYLAND_GIT_SRCREV} file://chromium-wayland/0006-Remove-GBM-support-from-wayland.gyp.patch"

OZONE_WAYLAND_GIT_FORK   = "git://github.com/jaragunde/ozone-wayland.git"
OZONE_WAYLAND_GIT_BRANCH = "wip/multi-seat"
OZONE_WAYLAND_GIT_SRCREV = "3af3d4eee5f48a420e90fae6de54594ec31d27d6"
SRC_URI_append = " ${OZONE_WAYLAND_GIT_FORK};destsuffix=${OZONE_WAYLAND_GIT_DESTSUFFIX};branch=${OZONE_WAYLAND_GIT_BRANCH};rev=${OZONE_WAYLAND_GIT_SRCREV}"
