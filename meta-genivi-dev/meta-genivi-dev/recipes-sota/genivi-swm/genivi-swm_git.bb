DESCRIPTION = "Genivi Software Loading Manager"
SECTION = "base"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=b278a92d2c1509760384428817710378"

SRCREV = "987c2f77da7e8bcfa212b47b6723497c7acc8f46"
SRC_URI = "\
    git://github.com/GENIVI/genivi_swm;branch=master;protocol=https \
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
    ${bindir}/sota-demo-reset.sh \
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

inherit systemd useradd
SYSTEMD_SERVICE_${PN} = "package_manager.service partition_manager.service module_loader_ecu1.service software_loading_manager.service lifecycle_manager.service"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r swm"

do_install () {
    install -o swm -d ${D}${libdir}/${PN}

    # TODO: Probably want to just copy in the needed files
    cp --no-preserve=ownership -r ${S}/* ${D}${libdir}/${PN}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 -c "${WORKDIR}/package_manager.service" ${D}${systemd_system_unitdir}
    install -m 0644 -c "${WORKDIR}/partition_manager.service" ${D}${systemd_system_unitdir}
    install -m 0644 -c "${WORKDIR}/module_loader_ecu1.service" ${D}${systemd_system_unitdir}
    install -m 0644 -c "${WORKDIR}/software_loading_manager.service" ${D}${systemd_system_unitdir}
    install -m 0644 -c "${WORKDIR}/lifecycle_manager.service" ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/dbus-1/system.d/
    install -m 0644 -c "${WORKDIR}/org.genivi.SoftwareLoadingManager.conf" ${D}${sysconfdir}/dbus-1/system.d

    install -d ${D}${bindir}
    install -m 0755 -c "${S}/nano_samples_rpi/sota-demo-reset.sh" ${D}${bindir}
}
