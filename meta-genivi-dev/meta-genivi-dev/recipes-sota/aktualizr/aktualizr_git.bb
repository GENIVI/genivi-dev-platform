SUMMARY = "Aktualizr SOTA Client"
DESCRIPTION = "SOTA Client written in C++"
HOMEPAGE = "https://github.com/advancedtelematic/aktualizr"
SECTION = "base"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit cmake systemd useradd

S = "${WORKDIR}/git"

SRCREV = "d303506d785383ff139e82ead7605dbe6ac26c2f"

SRC_URI = " \
    git://github.com/advancedtelematic/aktualizr \
    file://aktualizr.service \
    "

SYSTEMD_SERVICE_${PN} = "aktualizr.service"

DEPENDS = "boost curl openssl jansson dbus"

EXTRA_OECMAKE = "-DWARNING_AS_ERROR=OFF -DCMAKE_BUILD_TYPE=Release -DBUILD_TESTS=OFF -DBUILD_GENIVI=ON"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --shell /bin/false aktualizr"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/build/aktualizr ${D}${bindir}/
    install -d ${D}${sysconfdir}/dbus-1/system.d/
    install -c ${S}/dbus/org.genivi.SotaClient.conf ${D}${sysconfdir}/dbus-1/system.d
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/aktualizr.service ${D}${systemd_system_unitdir}

    mkdir ${D}${sysconfdir}/sota_certificates
    chown aktualizr.aktualizr ${D}${sysconfdir}/sota_certificates
}

FILES_${PN} = " \
    ${sysconfdir}/dbus-1/system.d/org.genivi.SotaClient.conf \
    ${bindir}/aktualizr \
    ${systemd_system_unitdir}/aktualizr.service \
    ${sysconfdir}/sota_certificates \
    "
