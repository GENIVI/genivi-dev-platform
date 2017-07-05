FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

DEPENDS_append = " pulseaudio"

SRC_URI_append = "\
    file://AudioManager_user.service \
    file://0001-Temporarily-code-block.patch \
"

EXTRA_OECMAKE += "\
    -DWITH_ENABLED_IPC=DBUS \
    -DWITH_DATABASE_STORAGE=OFF \
"

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/AudioManager_user.service ${D}/etc/systemd/user/AudioManager.service
    mkdir -p ${D}/etc/systemd/user/default.target.wants
    ln -sf /etc/systemd/user/AudioManager.service ${D}/etc/systemd/user/default.target.wants/AudioManager.service
}


FILES_${PN} += "\
    ${systemd_unitdir}/AudioManager.service \
    ${systemd_unitdir}/scripts/setup_amgr.sh \
    ${sysconfdir}/systemd/user \
    ${sysconfdir}/systemd/user/default.target.wants \
    "

FILES_${PN}-dev_remove = "${libdir}/*"
FILES_${PN}-dev += "${libdir}/cmake"
