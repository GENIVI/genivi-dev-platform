FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = " \
    file://systemd-user-enable-optional-pam_systemd.so-session.patch \
    file://system.conf \
    file://focused.target \
    file://unfocused.target \
    file://lazy.target \
    "

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/system.conf ${D}${sysconfdir}/systemd/system.conf

    install -m 0644 ${WORKDIR}/focused.target   ${D}${systemd_system_unitdir}/focused.target
    install -m 0644 ${WORKDIR}/unfocused.target ${D}${systemd_system_unitdir}/unfocused.target
    install -m 0644 ${WORKDIR}/lazy.target      ${D}${systemd_system_unitdir}/lazy.target
}
