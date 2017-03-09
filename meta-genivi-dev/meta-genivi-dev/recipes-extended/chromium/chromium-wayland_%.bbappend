FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://google-chrome.desktop \
"

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

# Apply same TUNE_FEATURES as in an armv7a build
ARMFPABI_armv7ve = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'arm_float_abi=hard', 'arm_float_abi=softfp', d)}"

# Remove cortexa7 optimization that conflicts with Chromium's hardcoded ARM flags
TUNE_FEATURES_remove_armv7ve = "cortexa7"
