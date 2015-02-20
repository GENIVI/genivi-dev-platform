# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "Simple QML Application"
DEPENDS = "qtbase qtdeclarative"

SRC_URI_append ="\
    file://qml-example.service \
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
