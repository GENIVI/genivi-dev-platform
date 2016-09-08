SUMMARY = "automotive message broker"
DESCRIPTION = "Automotive-message-broker abstracts the details of the network \
away from applications and provides a standard API for applications to easily \
get the required information"
HOMEPGAE = "https://github.com/otcshare/automotive-message-broker/wiki"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=b42382de5d854b9bb598acf2e8827de3"

inherit cmake systemd

PV = "0.14+git${SRCPV}"

DEPENDS = "glib-2.0 util-linux sqlite3 qtbase boost json-c libtool gpsd"

SRC_URI = "git://github.com/otcshare/automotive-message-broker.git;branch=0.14"
SRCREV = "c216955d16ca275159891cad296217094d972390"

SRC_URI += "file://amb_allow_sessionbus.patch \
            file://ambd.service \
            "

EXTRA_OECMAKE += "-Denable_icecc=OFF"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "ambd.service"

S = "${WORKDIR}/git"

do_install_append() {
    mv ${D}/usr/include/amb/* ${D}/usr/include

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/ambd.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += "${systemd_unitdir}/ambd.service"

INSANE_SKIP_${PN} = "dev-deps"
