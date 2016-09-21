SUMMARY = "Genivi AudioManager"
HOMEPAGE = "https://www.genivi.org/"
SECTION = "multimedia"

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://LICENCE;md5=f164349b56ed530a6642e9b9f244eec5"
PR = "r1"

DEPENDS = "common-api-c++-dbus dlt-daemon sqlite3 dbus node-state-manager"

SRCREV = "8725157e248c6706de59a02996f869b6ccdccb13"
SRC_URI = " \
    git://git.projects.genivi.org/AudioManager.git;branch=master;protocol=http \
    file://AudioManager.service \
    file://setup_amgr.sh \
    file://0001-audiomanager-fix-lib-install-path-for-multilib.patch \
    "
S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "AudioManager.service"
SYSTEMD_AUTO_ENABLE = "disable"

OECMAKE_CXX_FLAGS +="-ldl"
EXTRA_OECMAKE = " -DWITH_TESTS=OFF"

FILES_${PN} = " \
    ${bindir}/* \
    ${systemd_unitdir}/AudioManager.service \
    ${systemd_unitdir}/scripts/setup_amgr.sh \
    "
FILES_${PN}-dev += " \
    ${libdir}/* \
    /usr/share/cmake/Modules/* \
    "
do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        mkdir -p ${D}${systemd_unitdir}/scripts/
        install -m 0755 ${WORKDIR}/setup_amgr.sh ${D}${systemd_unitdir}/scripts/setup_amgr.sh
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/AudioManager.service ${D}${systemd_unitdir}/system
    fi

    install -d 0755 ${D}/usr/share/cmake/Modules
    for i in `ls ${S}/cmake/*.cmake`; do
        install -m 0644 ${i} ${D}/usr/share/cmake/Modules
    done
    perl -pi -e 's|COMMAND find "/usr/local/share/CommonAPI-\${CommonAPI_VERSION}"|COMMAND find "${PSEUDO_PREFIX}/share"|' \
      ${D}/usr/share/cmake/Modules/CommonAPI.cmake

    C_CMAKE=${D}${libdir}/cmake/audiomanagerConfig.cmake
    perl -pi -e 's|;${S}/cmake||' ${C_CMAKE}
    perl -pi -e 's|;(.*)/usr/share/cmake/Modules/||' ${C_CMAKE}
    perl -pi -e 's|set\(WITH_TESTS|#set\(WITH_TESTS|' ${C_CMAKE}
    perl -pi -e 's|.*set_and_check\(GOOGLE_MOCK_PROJECT_FOLDER \"(.+)\"\)\n||' ${C_CMAKE}
    perl -pi -e 's|GOOGLE_TEST_INCLUDE_DIR \"(.+)\"|GOOGLE_TEST_INCLUDE_DIR \"${PKG_CONFIG_SYSROOT_DIR}/usr/include/gtest\"|' ${C_CMAKE}
    perl -pi -e 's|GMOCK_INCLUDE_DIR \"(.+)\"|GMOCK_INCLUDE_DIR \"${PKG_CONFIG_SYSROOT_DIR}/usr/include/gmock\"|' ${C_CMAKE}
#   perl -pi -e \
#     's/set_and_check\(CMAKE_MODULE_PATH/#set_and_check\(CMAKE_MODULE_PATH/' \
#     ${D}${libdir}/cmake/audiomanagerConfig.cmake
}

python do_qa_staging() {
    bb.note("QA checking staging - SKIP")
}