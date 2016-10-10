# Copyright (C) 2015-2016 GENIVI Alliance

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/RobertAJMarshall/FMRadio"
SRCREV  = "257a1f0d6ab4a83c0588051ca6aaba22c9bf63b6"

SUMMARY = "FM Radio"
DEPENDS = "qtbase qtdeclarative"


S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "fm-radio-app"

include ics-apps.inc

do_install_append() {
     install -d ${WORKDIR}/git/imports \
                ${D}/opt/${APP}/bin/imports
}
