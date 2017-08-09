SUMMARY = "SocketCAN over Ethernet tunnel using UDP to transfer CAN frames between two machines"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPLv2;md5=B234EE4D69F5FCE4486A80FDAF4A4263"
PR = "r0"

DEPENDS = " \
           ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
          "

SRC_URI = "git://github.com/mguentner/cannelloni \
           file://launch_cannelloni.sh \
           file://launch_cannelloni.service "
SRCREV = "0fb6880b719b8acf2b4210b264b7140135e4be8a"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release"

inherit pkgconfig cmake

inherit systemd

SYSTEMD_SERVICE_${PN} = "launch_cannelloni.service"
INITSCRIPT_NAME = "launch_cannelloni.sh"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 cannelloni ${D}${bindir}

    install -m 0755 ${WORKDIR}/launch_cannelloni.sh ${D}${bindir}

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/launch_cannelloni.service ${D}${systemd_unitdir}/system
}

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"
