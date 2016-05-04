# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "Simple QML Application"
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
