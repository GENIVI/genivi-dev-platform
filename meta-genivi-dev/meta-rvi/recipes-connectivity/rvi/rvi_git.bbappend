FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# NOTE! install_to_usr branch might be temporary.
# When updating next time, consider changing to master or develop
# (assuming that installing to /usr has been merged, that is)
SRC_URI = "\
    git://github.com/GENIVI/rvi_core;branch=install_to_usr;name=rvi;protocol=https \
    file://rvi.service \
    file://device_id \
    file://0001-rvi_ctl.template-get-rid-of-dependecy-on-install.patch \
    "

RVI_DIR = "${datadir}/rvi_core"

do_install_append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/rvi.service ${D}${systemd_system_unitdir}

    # install RVI default configuration file
    install -d ${D}${sysconfdir}/rvi/
    install -m 0644 ${WORKDIR}/device_id ${D}${sysconfdir}/rvi/device_id
    install -m 0644 ./priv/config/rvi_yocto.config ${D}${sysconfdir}/rvi.config

    rm ${D}${sysconfdir}/init.d/rvi
    rm -r ${D}${sysconfdir}/opt
}

inherit systemd

INITSCRIPT_NAME = ""
INITSCRIPT_PARAMS = ""

pkg_postinst_${PN} () {
    #!/bin/sh -e
    true
}

FILES_${PN} += "${datadir}/rvi_core"
