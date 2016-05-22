FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

DEPENDS_append = " pulseaudio"

SRCREV_am = "8725157e248c6706de59a02996f869b6ccdccb13"
SRCREV_amp = "a0ed3b8f05147e9240d941655488d505057bbae7"

SRC_URI_remove = "git://git.projects.genivi.org/AudioManager.git;branch=master"

SRC_URI_append = " git://git.projects.genivi.org/AudioManager.git;branch=master;protocol=http;name=am \
                   git://git.projects.genivi.org/AudioManagerPlugins.git;destsuffix=git/Plugins;branch=master;protocol=http;name=amp \
                   file://sqlite_database_handler_change_mainVolume_to_volume.patch \
                   file://AudioManager_user.service \
                   file://fix_dbus_plugins.patch \
                   file://0001-Porting-Pulse-Routing-Interface-from-AM-v1.x-to-AM-v.patch;patchdir=${S}/Plugins \
                   file://0001-Porting-Pulse-Control-Interface-from-AM-v1.x-to-AM-v.patch;patchdir=${S}/Plugins \
                   file://0001-Temporarily-code-block-for-rpi2.patch \
                 "

EXTRA_OECMAKE += " \
    -DWITH_PULSE_CONTROL_PLUGIN=ON \
    -DWITH_TEST_CONTROLLER=OFF \
    -DWITH_ENABLED_IPC=DBUS \
    -DWITH_DATABASE_STORAGE=OFF \
    -DWITH_COMMAND_INTERFACE_CAPI=OFF \
    -DWITH_COMMAND_INTERFACE_DBUS=ON \
    -DWITH_ROUTING_INTERFACE_CAPI=OFF \
    -DWITH_ROUTING_INTERFACE_DBUS=ON \
    -DWITH_ROUTING_INTERFACE_PULSE=ON \
    "

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/AudioManager_user.service ${D}/etc/systemd/user/AudioManager.service
    mkdir -p ${D}/etc/systemd/user/default.target.wants
    ln -sf /etc/systemd/user/AudioManager.service ${D}/etc/systemd/user/default.target.wants/AudioManager.service
}


FILES_${PN} += " \
    ${libdir}/${PN}/command \
    ${libdir}/${PN}/control \
    ${libdir}/${PN}/routing \
    ${systemd_unitdir}/AudioManager.service \
    ${systemd_unitdir}/scripts/setup_amgr.sh \
    ${sysconfdir}/systemd/user \
    ${sysconfdir}/systemd/user/default.target.wants \
    ${datadir}/${PN}/${PN} \
    "

FILES_${PN}-dev_remove = "${libdir}/*"
FILES_${PN}-dev += "${libdir}/${PN}/cmake"

FILES_${PN}-dbg += " \
    ${libdir}/audiomanager/command/.debug/* \
    ${libdir}/audiomanager/control/.debug/* \
    ${libdir}/audiomanager/routing/.debug/* \
    "
