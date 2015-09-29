# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GENIVI Demo Platform HMI package group"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

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
    genivi-demo-platform-hmi \
    qml-example \
    gdp-hmi-mouse-test \
    "
