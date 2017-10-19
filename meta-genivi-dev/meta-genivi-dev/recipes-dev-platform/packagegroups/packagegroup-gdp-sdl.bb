DESCRIPTION = "Smart Device Link (SDL) package group"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-sdl \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    sdl-core \
    sdl-generic-hmi \
    "
