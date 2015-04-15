SUMMARY = "GENIVI AudioManager Monitor"
DESCRIPTION = "Monitor APP of the GENIVI AudioManager"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://README;md5=46736f88cb8ceb22cfab5d6007bd0cea"
DEPENDS = "qtbase qtdeclarative pulseaudio audiomanager"

BRANCH="master"

SRC_URI = "\
    git://git.projects.genivi.org/AudioManagerDemo.git;branch=${BRANCH} \
    file://AudioManager_Monitor.service \
    "
SRCREV = "7d989b3024428e47a7514d224d65e5209cdb7241"

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
