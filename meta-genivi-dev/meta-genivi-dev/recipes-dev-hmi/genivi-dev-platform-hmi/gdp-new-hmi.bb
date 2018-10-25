DESCRIPTION = "Reference HMI application for GDP"
HOMEPAGE = "https://github.com/GENIVI/hmi-layout-gdp"

LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

SRC_URI = "\
    git://github.com/GENIVI/hmi-layout-gdp.git \
    file://gdp-new-hmi.service \
"

SRCREV = "95aadfb33d95e14030585c3fa2e1afb0e7b743c8"

S = "${WORKDIR}/git"

DEPENDS = "\
    dbus-c++ \
    ivi-logging \
    qtbase \
    qtdeclarative \
    qtquick1 \
    systemd \
    wayland-ivi-extension \
"

RDEPENDS_${PN} = "qtbase qtsvg"

inherit autotools pkgconfig qmake5

do_install_append() {
    install -d ${D}${systemd_user_unitdir}
    install -p -D ${WORKDIR}/gdp-new-hmi.service ${D}${systemd_user_unitdir}/gdp-new-hmi.service
    install -d ${D}${sysconfdir}/systemd/user/default.target.wants
    ln -sf ${systemd_user_unitdir}/gdp-new-hmi.service ${D}${sysconfdir}/systemd/user/default.target.wants

    install -d ${D}${bindir}
    mv ${D}/opt/gdp-hmi/bin/gdp-hmi ${D}${bindir}/gdp-hmi
    rm -rf ${D}/opt
}

FILES_${PN} += "\
    ${libdir}/qt5/qml/com/genivi/ \
    ${systemd_user_unitdir}/gdp-new-hmi.service \
"
