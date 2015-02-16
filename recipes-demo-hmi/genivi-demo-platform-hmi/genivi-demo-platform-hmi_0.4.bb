# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-demo-platform-hmi.inc

SUMMARY = "Simple HMI for the GENIVI Demo Platform (GDP)"
DEPENDS = "dbus-c++ systemd wayland-ivi-extension"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
