DESCRIPTION = "GENIVI AudioManager Proof-of-Concept (PoC) package group"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-am-poc \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    alsa-state \
    audiomanager-poc \
    libasound-module-conf-pulse \
    libasound-module-ctl-pulse \
    libasound-module-pcm-pulse \
    pulseaudio-module-loopback \
    pulseaudio-module-native-protocol-tcp \
    pulseaudio-module-pipe-sink \
    pulseaudio-module-remap-sink \
    "
