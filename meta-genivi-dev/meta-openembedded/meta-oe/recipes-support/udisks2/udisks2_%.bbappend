FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://org.freedesktop.UDisks2.conf \
    file://udisks2-disk-manager.service \
    file://udisks2-disk-manager.sh \
"

do_install_append () {
    install -d ${D}${sysconfdir}/dbus-1/system.d
    install -m 644 ${WORKDIR}/org.freedesktop.UDisks2.conf \
                   ${D}${sysconfdir}/dbus-1/system.d/

    install -d ${D}${base_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/udisks2-disk-manager.service \
                    ${D}${systemd_system_unitdir}

    install -d ${D}${libexecdir}
    install -m 0755 ${WORKDIR}/udisks2-disk-manager.sh \
                    ${D}${libexecdir}/udisks2/udisks2-disk-manager.sh
}

PACKAGES += "${PN}-disk-manager"

SYSTEMD_PACKAGES += "${PN}-disk-manager"
SYSTEMD_SERVICE_${PN}-disk-manager = "udisks2-disk-manager.service"
SYSTEMD_AUTO_ENABLE_${PN}-disk-manager = "enable"

FILES_${PN} += "${sysconfdir}/dbus-1/system.d/org.freedesktop.UDisks2.conf"

FILES_${PN}-disk-manager = "\
    ${systemd_system_unitdir}/udisks2-disk-manager.service \
    ${libexecdir}/udisks2/udisks2-disk-manager.sh \
"
