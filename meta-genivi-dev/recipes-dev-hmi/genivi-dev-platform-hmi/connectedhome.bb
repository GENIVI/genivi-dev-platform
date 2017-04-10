# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
SRC_URI = "git://github.com/GENIVI/connected-home"
SRCREV  = "d839c4efb39ae0ad90e596d824564a0a27cf8ed7"

SUMMARY = "Connected Home"
DEPENDS = "qtbase qtdeclarative"

S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "qt-smart-demo"

include ics-apps.inc

