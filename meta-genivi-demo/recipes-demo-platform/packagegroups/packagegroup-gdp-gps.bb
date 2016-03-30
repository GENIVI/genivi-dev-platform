SUMMARY = "GNSS/GPS package group"
DESCRIPTION = "Package group bringing in packages needed to support \
Global Navigation Satellite Systems (GNSS), for example the Global \
Positioning System (GPS)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-gps \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    gpsd \
    gps-utils \
    gpsd-conf \
    gpsd-dev \
    gpsd-gpsctl \
    gpsd-udev \
    libgps \
    libgpsd \
    python-pygps \
    "
