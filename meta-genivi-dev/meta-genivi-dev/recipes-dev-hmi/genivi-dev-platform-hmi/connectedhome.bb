# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
SRC_URI = "git://github.com/GENIVI/connected-home"
SRCREV  = "2847ab9547be807d0767c69b0c0df7c90461fc6e"


SUMMARY = "Connected Home"
DEPENDS = "qtbase qtdeclarative"

S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "qt-smart-demo"

SRC_URI_append ="\
    file://${APP}.desktop \
    "


do_install_append() {
     install -Dm 644 ${WORKDIR}/git/${APP}.svg \
                 ${D}/opt/${APP}/share/icons/${APP}.svg

     install -Dm 555 ${EXE} \
                 ${D}/opt/${APP}/bin/${EXE}

     install -Dm 644 ${WORKDIR}/${APP}.desktop \
                 ${D}/usr/share/applications/${APP}.desktop

     install -d ${WORKDIR}/git/imports \
                ${D}/opt/${APP}/bin/imports
}

FILES_${PN} += "\
    /opt/${APP} \
    /usr/bin/${EXE} \
    ${libdir} \
    ${libdir}/systemd \
    ${libdir}/systemd/user \
    ${libdir}/systemd/user/* \
    "
