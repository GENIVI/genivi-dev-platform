# Copyright (C) 2017 GENIVI Alliance
# Released under the MIT license (see LICENSE for the terms)

DESCRIPTION = "Application used to pair bluetooth devices"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtbase qtdeclarative"

RDEPENDS_${PN} = "blueconnect"

inherit qmake5

SRC_URI = "git://github.com/genivi/bluetoothPairingApp;branch=master"
SRCREV = "ad09140f7fb2ce3056a3827729bc833fbb485e8f"

APP = "com.genivi.gdp.bluetoothPairingApp"
EXE = "bluetoothPairingApp"
DESKTOP_FILE = "${APP}.desktop"
SRC_URI_append = " file://${DESKTOP_FILE}"

S = "${WORKDIR}/git"

do_install_append() {
     install -d ${D}/opt/${APP}/bin
     install -m 0555 ${EXE} \
                ${D}/opt/${APP}/bin/${EXE}

     install -m 0644 ${S}/bluetooth.svg \
                ${D}/opt/${APP}/bluetooth.svg

     install -d ${D}${datadir}/applications
     install -m 0444 ${WORKDIR}/${DESKTOP_FILE} \
                ${D}${datadir}/applications/${DESKTOP_FILE}
}

FILES_${PN} += "\
    /opt/${APP} \
    "
