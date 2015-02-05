SUMMARY = "GENIVI AudioManager PoC"
DESCRIPTION = "Proof-of-Concept of the GENIVI AudioManager"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://AudioManagerPoC/README;md5=be0fe05c89ff7a2632eb080f72e8c129"

DEPENDS = "qtbase qtdeclarative qtwebkit"

BRANCH = "Intreprid_stable_branch"
VERSION = "6.1"

SRC_URI = "\
    git://git.projects.genivi.org/AudioManager.git;branch=${BRANCH};tag=${VERSION} \
    file://0001-AudioManagerPoC-GENIVI-Alliance-Audio-Manager-proof-.patch \
    file://0002-AudioManagerPoC-add-support-for-Wayland-ivi-shell.patch \
    file://AudioManager_PoC.service \
    "

S = "${WORKDIR}/git"

PATCHTOOL = "git"

QMAKE_PROFILES = "${S}/AudioManagerPoC"

inherit qmake5

do_install_append() {
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/AudioManager_PoC.service ${D}/etc/systemd/user
}

FILES_${PN} += "/opt/audiomanager-poc/*"
FILES_${PN}-dbg += "/usr/bin/am-poc/.debug/*"
