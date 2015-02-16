# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "GENIVI Demo Platform HMI - Panel"
DEPENDS = "qtbase qtdeclarative"

S = "${WORKDIR}/git/app/gdp-hmi-panel"

inherit qmake5

FILES_${PN} += "\
    ${datadir}/gdp/* \
    "

do_install_append() {
	install -d ${D}${datadir}/gdp
	install -m 0444 ${S}/assets/GDP_Quadrat.png \
					${D}${datadir}/gdp/GDP_Quadrat.png
	install -m 0444 ${S}/assets/GDP_Panel.png ${D}${datadir}/gdp/GDP_Panel.png
}
