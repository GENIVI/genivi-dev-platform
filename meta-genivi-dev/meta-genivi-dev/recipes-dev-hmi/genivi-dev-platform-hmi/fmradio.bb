# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/GENIVI/FMRadio"
SRCREV  = "9de0fa8be543ff556ee46d906081ace93f603724"

SUMMARY = "FM Radio"
DEPENDS = "qtbase qtdeclarative"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins"

SRC_URI_append ="\
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
