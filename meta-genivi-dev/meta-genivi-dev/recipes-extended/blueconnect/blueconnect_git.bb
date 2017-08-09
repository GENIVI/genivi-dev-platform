# Copyright (C) 2017 GENIVI Alliance
# Released under the MIT license (see LICENSE for the terms)

DESCRIPTION = "Library for creating simple qt bluetooth demos"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtbase qtdeclarative"

inherit qmake5

SRC_URI = "git://github.com/pelagicore/blueconnect;branch=master"
SRCREV = "a14c30d5a45a7864ed942386801bb070b6128dd0"

S = "${WORKDIR}/git"

FILES_${PN} +=  "${libdir}/qt5/qml/com/pelagicore/bluetooth/*"
