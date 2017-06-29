FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "\
    file://rvi.service \
    file://device_id \
    "

do_install_append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rvi.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/device_id ${D}${sysconfdir}/opt/rvi/device_id
    rm ${D}${sysconfdir}/init.d/rvi
}

inherit systemd

INITSCRIPT_NAME = ""
INITSCRIPT_PARAMS = ""

pkg_postinst_${PN} () {
    #!/bin/sh -e
    true
}
