# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "GENIVI DEV PLATFORM Historical Example App Metadata"
DEPENDS = "qtbase qtdeclarative dlt-daemon persistence-client-library"

HOMEPAGE = "http://projects.genivi.org/genivi-demo-platform/"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "git://github.com/GENIVI/gdp-hmi.git"
SRCREV  = "dcd70d67c656e6f457b1580bba3b3fe386ca3fbe"

SRC_URI_append ="\
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
    "

do_install_append() {
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

}
