DESCRIPTION = "Compilers and other devtools (on target)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-devtools \
    "

ALLOW_EMPTY_${PN} = "1"

IMAGE_INSTALL += "python-dev"
RDEPENDS_${PN} += "\
    git \
    cmake \
    python-pip \
    python3-pip \
    "
