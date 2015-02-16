# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "Simple QML Application"
DEPENDS = "qtbase qtdeclarative"

S = "${WORKDIR}/git/app/qml-example"

inherit qmake5
