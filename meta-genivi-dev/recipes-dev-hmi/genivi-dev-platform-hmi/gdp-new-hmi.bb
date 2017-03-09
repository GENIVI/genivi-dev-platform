SRC_URI = "git://github.com/GENIVI/hmi-layout-gdp.git"
SRCREV = "79a4f69e7588a6efc4b4c95abec7794216588087"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "dbus-c++ systemd wayland-ivi-extension qtquick1 qtbase"

RDEPENDS_${PN} += "qtbase qtsvg"

S = "${WORKDIR}/git"

inherit autotools pkgconfig qmake5 systemd

SYSTEMD_AUTO_ENABLE = "enable"

SRC_URI_append ="\
    file://gdp-new-hmi.service \
    "
SRC_URI_append_rcar-gen2 ="\
    file://0001-Remove-cxx11-strings.patch \
    "

FILES_${PN} += "\
    ${libdir}/* \
    /opt/gdp-hmi/bin/gdp-hmi \
    /usr/share/applications/* \
    ${systemd_unitdir}/* \
    /home/* \
"

do_install_append() {
        install -d ${D}${libdir}/systemd/user
        install -m 0444 ${WORKDIR}/gdp-new-hmi.service \
                        ${D}${libdir}/systemd/user
        mkdir -p ${D}/home/root/.config/systemd/user/default.target.wants/gdp-new-hmi.service
	ln -sf /usr/lib/systemd/user/gdp-new-hmi.service ${D}/home/root/.config/systemd/user/default.target.wants/gdp-new-hmi.service
	install -d ${D}/usr/share/applications/
        install -m 0444 ${WORKDIR}/git/manifests/* \
                        ${D}/usr/share/applications/
}
