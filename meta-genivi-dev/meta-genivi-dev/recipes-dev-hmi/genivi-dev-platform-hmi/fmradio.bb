# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/genivi/FMRadio"
SRCREV  = "7d30d07884921affc89901185db2ef743fb41909"

SUMMARY = "FM Radio"
DEPENDS = "qtbase qtdeclarative"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins"

SRC_URI_append ="\
    file://com.genivi.gdp.fmradio.desktop \
    "

S = "${WORKDIR}/git"

inherit qmake5

APP = "com.genivi.gdp.${PN}"
EXE = "fm-radio-app"

do_install_append() {
     install -d ${D}/opt/${APP}/share/icons
     install -m 0444 ${WORKDIR}/git/${APP}.svg \
             ${D}/opt/${APP}/share/icons/${APP}.svg

     install -d ${D}/opt/${APP}/bin
     install -m 0555 ${EXE} \
                ${D}/opt/${APP}/bin/${EXE}

     install -d ${D}/usr/share/applications
     install -m 0444 ${WORKDIR}/${APP}.desktop \
                ${D}/usr/share/applications/${APP}.desktop

     install -d ${D}/usr/lib/systemd/user/
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
