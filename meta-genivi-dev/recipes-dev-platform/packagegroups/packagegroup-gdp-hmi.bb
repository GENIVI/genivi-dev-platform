# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GENIVI Development Platform HMI package group"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-hmi \
    "

RDEPENDS_${PN} += "\
    gdp-hmi-background \
    gdp-hmi-launcher \
    gdp-hmi-launcher2 \
    gdp-hmi-panel \
    genivi-dev-platform-hmi \
    qml-example \
    gdp-hmi-mouse-test \
    gdp-new-hmi \
    "
