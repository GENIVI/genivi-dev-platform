# Copyright (C) 2015-2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "Connected Home"
HOMEPAGE = "https://github.com/GENIVI/connected-home"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS = "qtbase qtdeclarative"

SRC_URI = "\
    git://github.com/GENIVI/connected-home \
    file://${APPLICATION_DESKTOP_FILE} \
"
SRCREV  = "2847ab9547be807d0767c69b0c0df7c90461fc6e"

APPLICATION_DESKTOP_FILE = "com.genivi.gdp.${PN}.desktop"
APPLICATION_BIN = "${B}/qt-smart-demo"
APPLICATION_ICON = "${S}/com.genivi.gdp.${PN}.svg"
APPLICATION_UNIT = "com.genivi.gdp.connectedhome"

require apps.inc
