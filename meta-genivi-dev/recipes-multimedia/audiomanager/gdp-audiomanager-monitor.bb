SUMMARY = "GENIVI AudioManager Monitor"
DESCRIPTION = "Monitor APP of the GENIVI AudioManager"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"
DEPENDS = "qtbase qtdeclarative pulseaudio audiomanager"

BRANCH="master"

SRC_URI = "\
    git://git.projects.genivi.org/AudioManagerDemo.git;branch=${BRANCH};protocol=http \
    file://AudioManager_Monitor.service \
    file://0001-gdp-audio-monitor-include-fix.patch \
    "
SRCREV = "eea896440e5ad49622c7b1a4095f0d63c3465aa2"

S = "${WORKDIR}/git"

PATCHTOOL = "git"

QMAKE_PROFILES = "${S}/"

inherit qmake5

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/AudioManager_Monitor.service ${D}/etc/systemd/user
}

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN} += "/opt/AudioManagerMonitor/*"
FILES_${PN}-dbg += "/usr/bin/AudioManagerMonitor/.debug/*"
