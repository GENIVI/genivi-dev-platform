#
#   Copyright (C) 2015 Pelagicore AB
#
#   SPDX-License-Identifier: MIT
#
DESCRIPTION = "IVI-Logging is a software which facilitates the generation \
of log data. It provides an easy-to-use C++ API enabling you to send \
log data to various channels such as to the console, files, or GENIVI's \
DLT"
HOMEPAGE = "https://github.com/Pelagicore/ivi-logging"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"

PR = "r0"
SRC_URI = "${GIT_REPO};branch=master"
SRCREV = "7ec74df57744189bdccb77a184beceefaf446ba3"
PV = "0.1+git${SRCREV}"

DEPENDS = "glib-2.0"

inherit cmake

# DLT support is optional
PACKAGECONFIG ??= "dlt"
PACKAGECONFIG[dlt] = "-DENABLE_DLT_BACKEND=ON,,dlt-daemon,"

GIT_REPO = "git://github.com/Pelagicore/ivi-logging.git;protocol=https"
S = "${WORKDIR}/git"

FILES_${PN}-dev += " \
       ${libdir}/cmake/ \
       ${docdir}/ \
       "

