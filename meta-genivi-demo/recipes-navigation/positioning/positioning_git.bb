###########################################################################
# @licence app begin@
# SPDX-License-Identifier: MPL-2.0
#
# Component Name: Positioning
#
# Author: Marco Residori
#
# Copyright (C) 2013, XS Embedded GmbH
# 
# License:
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with
# this file, You can obtain one at http://mozilla.org/MPL/2.0/.
#
# @licence end@
###########################################################################

#
# Description: This is a Yocto recipe of the 4 proofs of concept contained 
#              in the positioning repository. 
#              Each PoC has its own sub-package. In this way it is possible to 
#              install only the PoC(s) that are of interest        
#
# Status: Work in Progress
#

DESCRIPTION = "Positioning"
HOMEPAGE = "http://projects.genivi.org/ivi-navigation"

LICENSE = "MPLv2"
LICENSE_${PN}-gnss= "MPLv2"
LICENSE_${PN}-sns = "MPLv2"
LICENSE_${PN}-repl = "MPLv2"
LICENSE_${PN}-enhpos = "MPLv2"

SRC_URI = "git://git.projects.genivi.org/lbs/positioning.git;protocol=http"
SRCREV = "fde1a780f531389d5a05e3b0486c98ad34df3dcb"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e73ca6874051c79a99d065bc57849af5"

S = "${WORKDIR}/git"

DEPENDS = "dbus dbus-c++ dbus-c++-native dlt-daemon gpsd"

inherit cmake pkgconfig 

PACKAGES =+ "${PN}-gnss ${PN}-gnss-test ${PN}-sns ${PN}-sns-test ${PN}-repl ${PN}-repl-test ${PN}-enhpos ${PN}-enhpos-test "

RDEPENDS_${PN}-repl-test = "${PN}-repl"
DEPENDS_${PN}-repl-test = "${PN}-repl"

RDEPENDS_${PN}-gnss-test = "${PN}-gnss"
DEPENDS_${PN}-gnss-test = "${PN}-gnss"

RDEPENDS_${PN}-sns-test = "${PN}-sns"
DEPENDS_${PN}-sns-test = "${PN}-sns"

RDEPENDS_${PN}-enhpos = "${PN}-gnss ${PN}-sns"
DEPENDS_${PN}-enhpos = "${PN}-gnss ${PN}-sns"

RDEPENDS_${PN}-enhpos-test = "${PN}-enhpos"
DEPENDS_${PN}-enhpos-test = "${PN}-enhpos"

do_configure() {
    cd ${S}/gnss-service && cmake -DWITH_DLT=OFF -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_TESTS=ON
    cd ${S}/sensors-service && cmake -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON
    cd ${S}/log-replayer && cmake -DWITH_DLT=OFF -DWITH_TESTS=ON
    cd ${S}/enhanced-position-service && cmake -DWITH_DLT=OFF -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON
}

do_compile() {
    cd ${S}/gnss-service && make
    cd ${S}/sensors-service && make
    cd ${S}/log-replayer && make
    cd ${S}/enhanced-position-service && make
}

do_install() {
    install -d ${D}/${bindir}
    install -d ${D}/${libdir}
    install -d ${D}${datadir}/${PN}
    install -m 755 ${S}/log-replayer/src/log-replayer ${D}/${bindir}
    install -m 755 ${S}/log-replayer/test/test-log-replayer ${D}/${bindir}
    install -m 644 ${S}/log-replayer/logs/*.log ${D}${datadir}/${PN}
    install -m 755 ${S}/gnss-service/src/*.so ${D}/${libdir}
    install -m 755 ${S}/gnss-service/test/gnss-service-client ${D}/${bindir}
    install -m 755 ${S}/gnss-service/test/compliance-test/gnss-service-compliance-test ${D}/${bindir}
    install -m 755 ${S}/sensors-service/src/*.so ${D}/${libdir}
    install -m 755 ${S}/sensors-service/test/sensors-service-client ${D}/${bindir}
    install -m 755 ${S}/enhanced-position-service/src/enhanced-position-service ${D}/${bindir}
    install -m 755 ${S}/enhanced-position-service/test/enhanced-position-client ${D}/${bindir}
    install -m 755 ${S}/enhanced-position-service/test/compliance-test/enhanced-position-service-compliance-test ${D}/${bindir}
    install -m 755 ${S}/enhanced-position-service/api/*.xml ${D}${datadir}/${PN}
}

FILES_${PN}-gnss = "${libdir}/libgnss-service*.so "
FILES_${PN}-gnss-test = "${bindir}/gnss-service-client \
                         ${bindir}/gnss-service-compliance-test "

FILES_${PN}-sns = "${libdir}/libsensors-service*.so "
FILES_${PN}-sns-test = "${bindir}/sensors-service-client "

FILES_${PN}-repl = "${bindir}/log-replayer \
                    ${datadir}/${PN}/*.log "
FILES_${PN}-repl-test = "${bindir}/test-log-replayer "

FILES_${PN}-enhpos = "${bindir}/enhanced-position-service "
FILES_${PN}-enhpos-test = "${bindir}/enhanced-position-client \
                           ${bindir}/enhanced-position-service-compliance-test "

BBCLASSEXTEND = "native"

#TODO: fix this
do_package_qa() {
  echo "workaround to avoid problem with RPATH"
}
