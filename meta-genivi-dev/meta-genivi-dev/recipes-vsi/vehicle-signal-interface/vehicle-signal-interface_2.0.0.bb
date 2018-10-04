SUMMARY = "Vehicle Signal Interface libraries"
DESCRIPTION = "Library for to distribute vehicle signals between \
components using a fast shared-memory Inter-Process communication."

HOMEPAGE = "https://github.com/GENIVI/vehicle_signal_interface"
SECTION = "base"

SRC_URI = "git://github.com/GENIVI/vehicle_signal_interface.git"
SRCREV = "7e8fc2c3303f71478b403b00a7f8d21486fdb394"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit cmake

DEPENDS = "python"

S = "${WORKDIR}/git"

FILES_${PN} = " \
    ${libdir}/* \
    ${bindir}/vsi-socketcand \
"

FILES_${PN}-dev = " \
    /usr/src/debug/* \
"

FILES_${PN}-dbg = " \
    ${libdir}/.debug/* \
"
