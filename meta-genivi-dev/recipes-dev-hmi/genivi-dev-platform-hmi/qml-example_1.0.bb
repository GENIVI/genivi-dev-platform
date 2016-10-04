# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "GENIVI DEV PLATFORM Simple QML Application"
HOMEPAGE = "http://projects.genivi.org/genivi-demo-platform/"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"
SRC_URI = "git://github.com/GENIVI/gdp-hmi.git"
SRCREV  = "dcd70d67c656e6f457b1580bba3b3fe386ca3fbe"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://qml-example.service \
    "

SRC_URI_append_qemux86-64 ="\
    file://0004-qml_example-Make-Graphic-working-on-Qemu-machine.patch \
    "
S = "${WORKDIR}/git/app/qml-example"

inherit qmake5

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/qml-example.service \
	                ${D}${libdir}/systemd/user
}

FILES_${PN} += "\
    ${libdir}/systemd/user/* \
    "
