FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "file://dbus_user.service \
                  file://dbus_user.socket \
                  file://dbus.conf \
                 "

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/dbus_user.service ${D}/etc/systemd/user/dbus.service
    cp ${WORKDIR}/dbus_user.socket ${D}/etc/systemd/user/dbus.socket
    mkdir -p ${D}/etc/systemd/user/default.target.wants
    ln -sf /etc/systemd/user/dbus.socket ${D}/etc/systemd/user/default.target.wants/dbus.socket
    mkdir -p ${D}/etc/systemd/system/user\@.service.d
    cp ${WORKDIR}/dbus.conf ${D}/etc/systemd/system/user\@.service.d
}
