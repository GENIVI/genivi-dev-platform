DESCRIPTION = "Genivi Software Loading Manager"
SECTION = "base"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=8d2127b230519ce4ad2c16070bdeb7b3"

SRCREV = "1ccca6c731da1e26752fbf2d2cdef18fe7e7fbb3"
SRC_URI = "\
    git://github.com/advancedtelematic/genivi_swm;branch=gdp-build;protocol=https \
    file://package_manager.service \
    file://partition_manager.service \
    file://lifecycle_manager.service \
    file://software_loading_manager.service \
    file://module_loader_ecu1.service \
    file://org.genivi.SoftwareLoadingManager.conf \
    "

PR = "r0"

S = "${WORKDIR}/git"

FILES_${PN} = " \
    ${libdir}/${PN}/* \
    ${systemd_system_unitdir}/package_manager.service \
    ${systemd_system_unitdir}/partition_manager.service \
    ${systemd_system_unitdir}/module_loader_ecu1.service \
    ${systemd_system_unitdir}/software_loading_manager.service \
    ${systemd_system_unitdir}/lifecycle_manager.service \
    ${sysconfdir}/dbus-1/system.d/org.genivi.SoftwareLoadingManager.conf \
    "

DEPENDS = "systemd"

RDEPENDS_${PN} = " \
    python-subprocess \
    python-pygobject \
    python-dbus \
    python-storm \
    squashfs-tools \
    rpm \
    "

inherit systemd
SYSTEMD_SERVICE_${PN} = "package_manager.service partition_manager.service module_loader_ecu1.service software_loading_manager.service lifecycle_manager.service"

do_install () {
    install -d ${D}${libdir}/${PN}

    # TODO: Probably want to just copy in the needed files
    cp -r ${S}/* ${D}${libdir}/${PN}

    install -d ${D}${systemd_system_unitdir}
    install -c "${WORKDIR}/package_manager.service" ${D}${systemd_system_unitdir}
    install -c "${WORKDIR}/partition_manager.service" ${D}${systemd_system_unitdir}
    install -c "${WORKDIR}/module_loader_ecu1.service" ${D}${systemd_system_unitdir}
    install -c "${WORKDIR}/software_loading_manager.service" ${D}${systemd_system_unitdir}
    install -c "${WORKDIR}/lifecycle_manager.service" ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/dbus-1/system.d/
    install -c "${WORKDIR}/org.genivi.SoftwareLoadingManager.conf" ${D}${sysconfdir}/dbus-1/system.d
}
