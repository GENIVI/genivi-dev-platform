DESCRIPTION = "GENIVI AudioManager Demo package group"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-am-demo \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    alsa-state \
    gdp-audiomanager-monitor \
    libasound-module-conf-pulse \
    libasound-module-ctl-pulse \
    libasound-module-pcm-pulse \
    pulseaudio-module-loopback \
    pulseaudio-module-native-protocol-tcp \
    pulseaudio-module-pipe-sink \
    pulseaudio-module-remap-sink \
    "
