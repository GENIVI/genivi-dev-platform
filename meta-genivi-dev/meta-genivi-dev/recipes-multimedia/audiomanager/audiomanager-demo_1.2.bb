SUMMARY = "GENIVI AudioManager Demo"
DESCRIPTION = "Demo of the GENIVI AudioManager"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://AudioManagerPoC/README;md5=be0fe05c89ff7a2632eb080f72e8c129"

DEPENDS = "qtbase qtdeclarative"

SRCREV = "daf851ee7a41d1b0572c0c95e15f61e427ce97f1"

SRC_URI = "\
    git://github.com/GENIVI/AudioManager.git;protocol=http \
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
