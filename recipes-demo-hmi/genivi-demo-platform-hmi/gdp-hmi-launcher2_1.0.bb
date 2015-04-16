# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "GENIVI Demo Platform HMI - Launcher 2"
DEPENDS = "qtbase qtdeclarative gdp-hmi-panel dlt-daemon persistence-client-library"

SRC_URI_append ="\
    file://gdp-hmi-launcher2.service \
    file://StartLauncher.service \
    file://start_launcher.sh \
    file://0001-gdp-hmi-launcher2-Change-the-name-of-Audiomanager-Mo.patch \
    "

S = "${WORKDIR}/git/app/gdp-hmi-launcher2"

inherit qmake5

FILES_${PN} += "\
    ${datadir}/gdp/* \
    ${libdir}/systemd/user/* \
    ${bindir} \
    /etc/systemd/user/* \
    "

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/gdp-hmi-launcher2.service \
	                ${D}${libdir}/systemd/user
	install -d ${D}${datadir}/gdp
	install -m 0444 ${S}/content/images/hmi_icons_033115-1.png \
		${D}${datadir}/gdp/hmi_icons_033115-1.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-2.png \
		${D}${datadir}/gdp/hmi_icons_033115-2.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-3.png \
		${D}${datadir}/gdp/hmi_icons_033115-3.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-4.png \
		${D}${datadir}/gdp/hmi_icons_033115-4.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-5.png \
		${D}${datadir}/gdp/hmi_icons_033115-5.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-6.png \
		${D}${datadir}/gdp/hmi_icons_033115-6.png

	install -m 0444 ${S}/content/images/hmi_icons_033115-1n.png \
		${D}${datadir}/gdp/hmi_icons_033115-1n.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-2n.png \
		${D}${datadir}/gdp/hmi_icons_033115-2n.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-3n.png \
		${D}${datadir}/gdp/hmi_icons_033115-3n.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-4n.png \
		${D}${datadir}/gdp/hmi_icons_033115-4n.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-5n.png \
		${D}${datadir}/gdp/hmi_icons_033115-5n.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-6n.png \
		${D}${datadir}/gdp/hmi_icons_033115-6n.png

	install -m 0444 ${S}/content/images/hmi_icons_033115-1s.png \
		${D}${datadir}/gdp/hmi_icons_033115-1s.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-2s.png \
		${D}${datadir}/gdp/hmi_icons_033115-2s.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-3s.png \
		${D}${datadir}/gdp/hmi_icons_033115-3s.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-4s.png \
		${D}${datadir}/gdp/hmi_icons_033115-4s.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-5s.png \
		${D}${datadir}/gdp/hmi_icons_033115-5s.png
	install -m 0444 ${S}/content/images/hmi_icons_033115-6s.png \
		${D}${datadir}/gdp/hmi_icons_033115-6s.png

	install -m 0444 ${S}/content/images/arrow-right.png \
		${D}${datadir}/gdp/arrow-right.png
	install -m 0444 ${S}/content/images/background_lab.jpg \
		${D}${datadir}/gdp/background_lab.jpg
	install -m 0444 ${S}/content/images/spot.png \
		${D}${datadir}/gdp/spot.png
	install -m 0444 ${S}/content/images/powerOff.png \
		${D}${datadir}/gdp/powerOff.png

	install -d ${D}/etc/systemd/user
	install -m 0444 ${WORKDIR}/StartLauncher.service \
		${D}/etc/systemd/user
	install -d ${D}/etc/systemd/user/default.target.wants
	ln -sf /etc/systemd/user/StartLauncher.service \
		${D}/etc/systemd/user/default.target.wants/StartLauncher.service
	install install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/start_launcher.sh ${D}/${bindir}
}
