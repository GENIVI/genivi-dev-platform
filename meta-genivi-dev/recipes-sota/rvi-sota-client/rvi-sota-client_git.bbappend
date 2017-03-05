FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "\
    file://sota.toml \
    file://sota_client.service \
    file://org.genivi.SotaClient.conf \
    "

do_install_append () {
    install -d ${D}${sysconfdir}
    install -c ${WORKDIR}/sota.toml ${D}${sysconfdir}
    install -m 0644 -c ${WORKDIR}/sota_client.service ${D}${systemd_unitdir}/system
    install -d ${D}${sysconfdir}/dbus-1/system.d/
    install -m 0644 -c ${WORKDIR}/org.genivi.SotaClient.conf ${D}${sysconfdir}/dbus-1/system.d
}

FILES_${PN} += " \
    ${sysconfdir}/sota.toml \
    ${sysconfdir}/dbus-1/system.d/org.genivi.SotaClient.conf \
    "
