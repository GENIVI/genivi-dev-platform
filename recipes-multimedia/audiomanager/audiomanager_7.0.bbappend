FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

DEPENDS_append = " pulseaudio"

SRC_URI_append = " git://git.projects.genivi.org/AudioManagerPlugins.git;destsuffix=git/Plugins;branch=${BRANCH};tag=${PV} \
                   file://0001-Porting-Pulse-Routing-Interface-from-AM-v1.x-to-AM-v.patch \
                   file://0001-Porting-Pulse-Control-Interface-from-AM-v1.x-to-AM-v.patch \
                   file://sqlite_database_handler_change_mainVolume_to_volume.patch \
                   file://AudioManager_user.service \
                   file://0001-Updated-PluginControlInterfacePulse-control-sender-t.patch \
                   file://fix_dbus_plugins.patch \
                 "

EXTRA_OECMAKE += " \
    -DWITH_PULSE_ROUTING_PLUGIN=ON \
    -DWITH_PULSE_CONTROL_PLUGIN=ON \
    -DWITH_ENABLED_IPC=DBUS \
    -DWITH_DATABASE_STORAGE=OFF \
    -DWITH_COMMAND_INTERFACE_CAPI=OFF \
    -DWITH_COMMAND_INTERFACE_DBUS=ON \
    -DWITH_ROUTING_INTERFACE_CAPI=OFF \
    -DWITH_ROUTING_INTERFACE_DBUS=ON \
    "

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/AudioManager_user.service ${D}/etc/systemd/user/AudioManager.service
    mkdir -p ${D}/etc/systemd/user/default.target.wants
    ln -sf /etc/systemd/user/AudioManager.service ${D}/etc/systemd/user/default.target.wants/AudioManager.service
}

FILES_${PN} += " \
    ${libdir}/audiomanager/command/*.so* \
    ${libdir}/audiomanager/control/*.conf \
    ${libdir}/audiomanager/control/*.so* \
    ${libdir}/audiomanager/routing/*.conf \
    ${libdir}/audiomanager/routing/*.so* \
    ${systemd_unitdir}/AudioManager.service \
    ${systemd_unitdir}/scripts/setup_amgr.sh \
    "

FILES_${PN}-dbg += " \
    ${libdir}/audiomanager/command/.debug/* \
    ${libdir}/audiomanager/control/.debug/* \
    ${libdir}/audiomanager/routing/.debug/* \
    "
