# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "GENIVI Development Platform HMI - Panel"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://gdp-hmi-panel.service \
    "

SRC_URI_append_qemux86-64 ="\
    file://0003-panel-Make-Graphic-working-on-Qemu-machine.patch \
    "

S = "${WORKDIR}/git/app/gdp-hmi-panel"

inherit qmake5

FILES_${PN} += "\
    ${datadir}/gdp/* \
    ${libdir}/systemd/user/* \
    "

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/gdp-hmi-panel.service \
	                ${D}${libdir}/systemd/user
	install -d ${D}${datadir}/gdp
	install -m 0444 ${S}/assets/GDP_Quadrat.png \
					${D}${datadir}/gdp/GDP_Quadrat.png
	install -m 0444 ${S}/assets/GDP_Panel.png ${D}${datadir}/gdp/GDP_Panel.png
}
