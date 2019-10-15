WESTON_INI_CONFIG=${sysconfdir}/xdg/weston

do_install_append() {
    install -d ${D}${WESTON_INI_CONFIG}
    install -m 0644 ${WORKDIR}/weston.ini ${D}${WESTON_INI_CONFIG}/weston.ini
}

FILES_${PN} += "${systemd_unitdir}/system/* \
      ${WESTON_INI_CONFIG}/weston.ini \
"

