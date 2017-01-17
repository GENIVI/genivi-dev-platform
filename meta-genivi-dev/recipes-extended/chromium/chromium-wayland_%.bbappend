FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://chromium.app \
    file://chromium.service \
"

# FIXME: workaround for https://at.projects.genivi.org/jira/browse/LM-2
CHROMIUM_EXTRA_ARGS_append = " --window-size=1728,1080"

do_install_append() {
    install -d ${D}${datadir}/applications/
    install -m 0444 ${WORKDIR}/chromium.app ${D}${datadir}/applications/

    install -d ${D}${libdir}/systemd/user
    install -m 0444 ${WORKDIR}/chromium.service ${D}${libdir}/systemd/user/

    for size in 22 24 48 64 128 256; do
        install -Dm 0644 ${S}/chrome/app/theme/chromium/product_logo_$size.png \
            ${D}${bindir}/${BPN}/product_logo_$size.png
    done
}
