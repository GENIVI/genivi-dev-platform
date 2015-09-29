# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Script to exercise the GDP HMI by clicking on buttons"
RDEPENDS_${PN} = "python-uinput"
FILESEXTRAPATHS_append := "${THISDIR}"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://gdp-hmi-mouse-test.py;md5=1d78d1640a1af932fd3b774b93be0ee6"

SRC_URI = "file://gdp-hmi-mouse-test.py"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0555 ${WORKDIR}/gdp-hmi-mouse-test.py ${D}${bindir}
}
