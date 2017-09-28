SUMMARY = "GENIVI AudioManager Monitor"
DESCRIPTION = "Monitor APP of the GENIVI AudioManager"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"
DEPENDS = "qtbase qtdeclarative pulseaudio audiomanager"

SRCREV = "eea896440e5ad49622c7b1a4095f0d63c3465aa2"

DESKTOP_FILE = "AudioManager_Monitor.desktop"

SRC_URI = "\
    git://github.com/GENIVI/audio-manager-demo.git \
    file://${DESKTOP_FILE} \
    file://0001-gdp-audio-monitor-include-fix.patch \
    "

S = "${WORKDIR}/git"

PATCHTOOL = "git"

QMAKE_PROFILES = "${S}/"

inherit qmake5

do_install_append() {
    install -Dm 644 ${WORKDIR}/${DESKTOP_FILE} \
                    ${D}/usr/share/applications/${DESKTOP_FILE}
}

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN} += "                           \
                /opt/AudioManagerMonitor/* \
                /usr/share/applications/*   \
"

FILES_${PN}-dbg += "/usr/bin/AudioManagerMonitor/.debug/*"
