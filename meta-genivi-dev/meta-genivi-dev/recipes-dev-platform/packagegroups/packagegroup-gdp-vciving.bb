DESCRIPTION = "VCIVING project and prerequisites, including -dev packages for on-target installations"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-vciving \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    alsa-dev \
    espeak \
    ffmpeg-dev \
    flac \
    gstreamer1.0-libav \
    portaudio-v19-dev \
    python3-dev \
    python3-pip \
    "
