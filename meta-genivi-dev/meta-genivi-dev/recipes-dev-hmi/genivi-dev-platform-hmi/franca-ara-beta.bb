# Copyright (C) 2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "Franca - ARA Integration Demo"
HOMEPAGE = "https://github.com/GENIVI/franca_ara_integration"
LICENSE  = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS = "qtbase qtdeclarative common-api-c++-someip"

REPO_URL = "github.com/GENIVI/franca_ara_integration"

SRC_URI = "\
    git://${REPO_URL};branch=master \
    file://${APPLICATION_DESKTOP_FILE} \
    https://${REPO_URL}/releases/download/1.0/video.tar;unpack=0 \
"

SRCREV = "50cd4402672b93244ab961174c01cd49fcd1016a"

SRC_URI[md5sum] = "7d9ad10d60a6cd84947f2acee685cf3d"
SRC_URI[sha256sum] = "0d394a854355215dee5a83f52e4a24d8fea84a2ea0ee8129d4f1505e4f9e2426"

APPLICATION_DESKTOP_FILE = "com.genivi.gdp.franca.ara.beta.desktop"
APPLICATION_BIN = "${B}/franca-ara-beta"
APPLICATION_ICON = "${S}/com.genivi.gdp.franca.ara.svg"
APPLICATION_UNIT = "com.genivi.gdp.franca.ara.beta"

require recipes-dev-hmi/genivi-dev-platform-hmi/apps.inc

PACKAGES += "${PN}-capi-server"

do_compile_append() {
    cd ${S}/tests/testprograms/capi_server

    $CXX -o capi_server -std=c++14 -I src-gen \
         main.cpp src-gen/v1/genivi/aasr/showcase/*.cpp \
         `pkg-config --cflags --libs CommonAPI-SomeIP` -lpthread
}

# Aligned with AppStream guidelines for install locations
do_install() {
    cp "${B}/franca-ara" "${B}/franca-ara-beta"

    install -d ${D}${APPLICATION_DIR}/${PN}/bin
    install -m 0555 ${APPLICATION_BIN} ${D}${APPLICATION_BIN_TARGET_PATH}

    install -d ${D}${datadir}/app-info/icons/${DISTRO_VERSION}/${APPLICATION_ICON_SIZE}/
    install -m 0644 ${APPLICATION_ICON} ${D}${APPLICATION_ICON_TARGET_PATH}

    sed -i -e 's#[@]APPLICATION_ICON_TARGET_PATH[@]#${APPLICATION_ICON_TARGET_PATH}#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}
    sed -i -e 's#[@]APPLICATION_BIN_TARGET_PATH[@]#/usr/local/bin/run_franca_ara_beta.sh#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}
    sed -i -e 's#[@]APPLICATION_UNIT[@]#${APPLICATION_UNIT}#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}

    install -d ${D}${datadir}/applications/
    install -m 0444 ${WORKDIR}/${APPLICATION_DESKTOP_FILE} \
                ${D}${datadir}/applications/
}

do_install_append() {
    install -d ${D}${prefix}/local/bin/
    install -m 755 ${S}/deploy/usr/local/bin/run_franca_ara_beta.sh ${D}${prefix}/local/bin/
    install -m 755 ${S}/tests/testprograms/capi_server/capi_server ${D}${prefix}/local/bin/

    install -d ${D}${prefix}/local/share/franca-ara/conf/
    install -m 644 ${S}/conf/ivi/vsomeip_ecu1.json ${D}${prefix}/local/share/franca-ara/conf/

    install -d ${D}${prefix}/local/share/franca-ara/setup/
    install -m 755 ${S}/deploy/usr/local/share/franca-ara/setup/set_fixed_ip.sh ${D}${prefix}/local/share/franca-ara/setup/
    install -m 755 ${S}/deploy/usr/local/share/franca-ara/setup/setup_multicast_route.sh ${D}${prefix}/local/share/franca-ara/setup/

    install -d ${D}${prefix}/local/share/franca-ara/images

    tar xvf ${WORKDIR}/video.tar -C ${D}${prefix}/local/share/franca-ara/images/
}

FILES_${PN} += "\
    ${prefix}/local/bin/run_franca_ara_beta.sh \
    ${prefix}/local/share/franca-ara/conf/ \
    ${prefix}/local/share/franca-ara/images/ \
    ${prefix}/local/share/franca-ara/setup/ \
"

FILES_${PN}-capi-server = "\
    ${prefix}/local/bin/capi_server \
"
INSANE_SKIP_${PN}-capi-server = "ldflags"
