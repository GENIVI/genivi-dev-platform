# Copyright (C) 2017 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "CDL Concept Demo"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
SRC_URI = "git://github.com/GENIVI/car-data-logger.git;protocol=https"
SRCREV  = "c63adbb4809319a53a934dbf9f344f75c738d570"

DEPENDS = "qtbase qtdeclarative vsomeip common-api-c++-someip dbus dlt-daemon common-api-c++-dbus common-api-c++"

S = "${WORKDIR}/git"

inherit qmake5

do_install_append() {
    install -d ${D}${datadir}/cdl
    mv ${D}/opt/cdl ${D}${datadir}/cdl

    # Clean-up
    rm -rf ${D}/opt
}

FILES_${PN} += "${datadir}/cdl"
INSANE_SKIP_${PN} = "dev-so libdir"
