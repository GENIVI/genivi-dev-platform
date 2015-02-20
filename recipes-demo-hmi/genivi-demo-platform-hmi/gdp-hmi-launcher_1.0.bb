# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "GENIVI Demo Platform HMI - Launcher"
DEPENDS = "qtbase qtdeclarative gdp-hmi-panel"

SRC_URI_append ="\
    file://gdp-hmi-launcher.service \
    "

S = "${WORKDIR}/git/app/gdp-hmi-launcher"

inherit qmake5

FILES_${PN} += "\
    ${datadir}/gdp/* \
    ${libdir}/systemd/user/* \
    "

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/gdp-hmi-launcher.service \
	                ${D}${libdir}/systemd/user
	install -d ${D}${datadir}/gdp
	install -m 0444 ${S}/assets/car-purple-icon.png \
		${D}${datadir}/gdp/car-purple-icon.png
	install -m 0444 ${S}/assets/power-icon_red.png \
		${D}${datadir}/gdp/power-icon_red.png
}
