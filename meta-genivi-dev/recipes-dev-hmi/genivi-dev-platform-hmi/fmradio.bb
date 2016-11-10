# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/GENIVI/FMRadio"
SRCREV  = "8da331f78fdca7a98f5b812c9f40231c868ed8f3"

SUMMARY = "FM Radio"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://0001-fmradio-remove-absolute-include-path.patch \
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
