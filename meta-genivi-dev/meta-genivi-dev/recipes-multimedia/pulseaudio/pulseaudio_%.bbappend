FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = " file://client_conf.patch \
                   file://daemon_conf.patch \
                   file://am_poc.pa \
                   file://pulseaudio_user.service \
"

do_install_append_intel-corei7-64() {
    sed -i -e "40s/device=hw:0,0/device=hw:0,3/" ${WORKDIR}/am_poc.pa
}

do_install_append() {
    cp ${WORKDIR}/am_poc.pa ${D}/etc/pulse
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/pulseaudio_user.service ${D}/etc/systemd/user/pulseaudio.service
    mkdir -p ${D}/etc/systemd/user/default.target.wants
    ln -sf /etc/systemd/user/pulseaudio.service ${D}/etc/systemd/user/default.target.wants/pulseaudio.service
}
