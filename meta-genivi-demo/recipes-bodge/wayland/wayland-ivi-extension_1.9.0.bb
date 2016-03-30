SUMMARY = "Wayland IVI Extension"
DESCRIPTION = "GENIVI Layer Management API based on Wayland IVI Extension"
HOMEPAGE = "http://projects.genivi.org/wayland-ivi-extension"
BUGTRACKER = "http://bugs.genivi.org/enter_bug.cgi?product=Wayland%20IVI%20Extension"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=176cedb32f48dd58f07e0c1c717b3ea4"

PR = "r1"

SRC_URI = "git://git.projects.genivi.org/${PN}.git;tag=${PV}"

DEPENDS = "weston"
S = "${WORKDIR}/git"

inherit cmake autotools

EXTRA_OECMAKE := "-DWITH_ILM_INPUT=1"

FILES_${PN} += "${libdir}/weston/*"
FILES_${PN}-dbg += "${libdir}/weston/.debug/*"

# for testing
SRC_URI_append += "file://remove_compile-host-path_issue.patch "
DEPENDS += " gtest"
EXTRA_OECMAKE += " -DBUILD_ILM_API_TESTS=1 -DINSTALL_ILM_API_TESTS=1"
FILES_${PN} += " ${bindir}/*"
