# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "GENIVI Development Platform HMI - Launcher"
DEPENDS = "qtbase qtdeclarative gdp-hmi-panel"

SRC_URI_append ="\
    file://gdp-hmi-launcher.service \
    "

SRC_URI_append_qemux86-64 ="\
    file://0002-launcher-Make-Graphic-working-on-Qemu-machine.patch \
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

	install -m 0444 ${S}/assets/1424451154_fuel-128.png \
		${D}${datadir}/gdp/1424451154_fuel-128.png
	install -m 0444 ${S}/assets/App-xeyes-icon.png \
		${D}${datadir}/gdp/App-xeyes-icon.png
	install -m 0444 ${S}/assets/GDP_AM_Button.png \
		${D}${datadir}/gdp/GDP_AM_Button.png
	install -m 0444 ${S}/assets/GDP_Browser_Button.png \
		${D}${datadir}/gdp/GDP_Browser_Button.png
	install -m 0444 ${S}/assets/maps-icon.png \
		${D}${datadir}/gdp/maps-icon.png
}
