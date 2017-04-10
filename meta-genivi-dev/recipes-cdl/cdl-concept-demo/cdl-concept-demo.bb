# Copyright (C) 2017 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "CDL Concept Demo"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
SRC_URI = "git://github.com/GENIVI/car-data-logger.git;branch=proof-of-concept;protocol=https"
SRCREV  = "14a44c1d0db2b02582bcdf9e38b0947677c6f485"

DEPENDS = "qtbase qtdeclarative vsomeip common-api-c++-someip dbus dlt-daemon common-api-c++-dbus common-api-c++"

S = "${WORKDIR}/git"

inherit qmake5

FILES_${PN} += "/opt/cdl/*"
INSANE_SKIP_${PN} = "dev-so"
