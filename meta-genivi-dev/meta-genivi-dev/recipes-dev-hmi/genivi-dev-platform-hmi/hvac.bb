# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"
SRC_URI = "git://github.com/GENIVI/HVAC"
SRCREV  = "dc5185223e7301807ce1a12c8f30528851fb4dc8"

SUMMARY = "HVAC"
DEPENDS = "qtbase qtdeclarative dbus"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins"

S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "HVAC_rvi_vtc1010"

SRC_URI_append ="\
    file://${APP}.desktop \
    "


do_install_append() {
     install -Dm 0444 ${WORKDIR}/git/${APP}.svg \
                 ${D}/opt/${APP}/share/icons/${APP}.svg

     install -Dm 0555 ${EXE} \
                 ${D}/opt/${APP}/bin/${EXE}

     install -Dm 0644 ${WORKDIR}/${APP}.desktop \
                 ${D}/usr/share/applications/${APP}.desktop
}

FILES_${PN} += "\
    /opt/* \
    ${libdir} \
    ${libdir}/systemd \
    ${libdir}/systemd/user \
    ${libdir}/systemd/user/* \
    /usr/share/* \
    "
