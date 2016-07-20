# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "GENIVI Development Platform HMI - Launcher 2"
DEPENDS = "qtbase qtdeclarative gdp-hmi-panel dlt-daemon persistence-client-library"

SRC_URI_append ="\
    file://gdp-hmi-launcher2.service \
    file://PowerOff.service \
    file://StartLauncher.service \
    file://StartLauncher.path \
    file://start_launcher.sh \
    file://power_off.sh \
    file://0001-gdp-hmi-launcher2-Change-the-name-of-Audiomanager-Mo.patch \
    "

SRC_URI_append_qemux86-64 ="\
    file://0005-Launcher2-Make-Graphic-working-on-Qemu-machine.patch \
    file://0006-Launcher2-Simplify-the-OpenGLes-part.patch \
    "

SRC_URI_append_dragonboard-410c = " file://0001-Fix-broken-resolution-issue.patch;patchdir=${WORKDIR}/git"

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
	install -m 0444 ${WORKDIR}/PowerOff.service \
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
	ln -sf /etc/systemd/user/StartLauncher.path \
		${D}/etc/systemd/user/default.target.wants/StartLauncher.path
	install -m 0444 ${WORKDIR}/StartLauncher.path \
		${D}/etc/systemd/user
	install install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/start_launcher.sh ${D}/${bindir}
	install -m 0755 ${WORKDIR}/power_off.sh ${D}/${bindir}
}
