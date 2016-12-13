# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/GENIVI/FMRadio"
SRCREV  = "256c49af8719e2831a316e18240670df66ea2964"

SUMMARY = "FM Radio"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://0002-Changed-surface-id-of-FM-radio.patch \
    "

S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "fm-radio-app"

include ics-apps.inc

do_install_append() {
     install -d ${WORKDIR}/git/imports \
                ${D}/opt/${APP}/bin/imports
}
