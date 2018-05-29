# Copyright (C) 2015-2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "FM Radio"
HOMEPAGE = "https://github.com/GENIVI/FMRadio"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "qtbase qtdeclarative"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins bubblewrap"

SRC_URI = "\
    git://github.com/genivi/FMRadio \
    file://${APPLICATION_DESKTOP_FILE} \
"
SRCREV  = "7d30d07884921affc89901185db2ef743fb41909"

APPLICATION_DESKTOP_FILE = "com.genivi.gdp.${PN}.desktop"
APPLICATION_BIN = "${B}/fm-radio-app"
APPLICATION_ICON = "${S}/com.genivi.gdp.${PN}.svg"
APPLICATION_UNIT = "com.genivi.gdp.fmradio"

require apps.inc
