FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = " \
    file://systemd-user-enable-optional-pam_systemd.so-session.patch \
    file://system.conf"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/system.conf ${D}${sysconfdir}/systemd/system.conf
}
