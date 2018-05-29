# Copyright (C) 2015-2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "GDP HVAC app with D-BUS interface"
HOMEPAGE = "https://github.com/GENIVI/HVAC"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS = "qtbase qtdeclarative dbus"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins"

SRC_URI = "\
    git://github.com/GENIVI/HVAC \
    file://${APPLICATION_DESKTOP_FILE} \
"
SRCREV  = "dc5185223e7301807ce1a12c8f30528851fb4dc8"

APPLICATION_DESKTOP_FILE = "com.genivi.gdp.${PN}.desktop"
APPLICATION_BIN = "${B}/HVAC_rvi_vtc1010"
APPLICATION_ICON = "${S}/com.genivi.gdp.${PN}.svg"
APPLICATION_UNIT = "com.genivi.gdp.hvac"

require apps.inc

do_install_append() {
    install -d ${D}${APPLICATION_DIR_TARGET_PATH}/imports
    cp -fr ${S}/imports/* ${D}${APPLICATION_DIR_TARGET_PATH}/imports
}
