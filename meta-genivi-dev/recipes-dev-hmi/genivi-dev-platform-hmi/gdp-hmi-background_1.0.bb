# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "GENIVI Development Platform Background Image"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://gdp-hmi-background.service \
    "

SRC_URI_append_qemux86-64 ="\
    file://0001-Background-Make-Graphic-working-on-Qemu-machine.patch \
"

SRC_URI_append_dragonboard-410c = " file://0001-Fix-broken-resolution-issue.patch;patchdir=${WORKDIR}/git"

S = "${WORKDIR}/git/app/gdp-hmi-background"

inherit qmake5

FILES_${PN} += "\
    ${datadir}/gdp/* \
    ${libdir}/systemd/user/* \
    "

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/gdp-hmi-background.service \
	                ${D}${libdir}/systemd/user
	install -d ${D}${datadir}/gdp
	install -m 0444 ${S}/assets/GDP_Background.png \
	                ${D}${datadir}/gdp/GDP_Background.png
}
