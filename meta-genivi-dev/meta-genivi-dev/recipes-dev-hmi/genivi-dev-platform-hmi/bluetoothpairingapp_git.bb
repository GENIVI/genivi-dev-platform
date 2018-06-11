# Copyright (C) 2017-2018 GENIVI Alliance
# Released under the MIT license (see LICENSE for the terms)
SUMMARY = "Simple application for pairing bluetooth devices"
HOMEPAGE = "https://github.com/genivi/bluetoothPairingApp"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtbase qtdeclarative"
RDEPENDS_${PN} = "blueconnect"

SRC_URI = "\
    git://github.com/genivi/bluetoothPairingApp;branch=master \
    file://${APPLICATION_DESKTOP_FILE} \
"
SRCREV = "ad09140f7fb2ce3056a3827729bc833fbb485e8f"

APPLICATION_DESKTOP_FILE = "com.genivi.gdp.${PN}.desktop"
APPLICATION_BIN = "${B}/bluetoothPairingApp"
APPLICATION_ICON = "${S}/bluetooth.svg"

require apps.inc
