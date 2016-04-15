SUMMARY = "Navigation software based on Navit and compliant with the Navigation APIs standardized by the GENIVI Alliance"
SRCREV = "9953bcd40cb4185eaaad1c15c486dd6220253990"
PV = "1"

SRC_URI = "git://git.projects.genivi.org/lbs/navigation.git;protocol=http \
           file://change_xml_generation_dir.patch \
           file://navit_genivi_mapviewer.xml \
           file://navit_genivi_navigationcore.xml \
          "
DEPENDS = "navit positioning"

S = "${WORKDIR}/git"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://src/navigation/LICENSE;md5=50772b2cd18ba00801e433556c1924bc"

inherit cmake


EXTRA_CMAKEFLAGS = "-Wno-dev -C ${STAGING_DATADIR}/cmake_plugin_settings.txt -Dapi_DIR=${S}/api -Dgenerated_api_DIR=${S}/api/include -Dnavit_SRC_DIR=${STAGING_LIBDIR} -Dpositioning_API=${STAGING_DATADIR}/positioning"

do_configure() {
    cd ${S}/api/map-viewer && cmake . && cd ${S}/api/navigation-core && cmake . && cd ${S}/api/poi-service && cmake .

    cd ${S}/src/navigation/map-viewer && cmake -Dnavit_SRC_DIR=${STAGING_LIBDIR}
    cd ${S}/src/navigation/map-viewer/configuration-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=ON -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_TESTS=ON .
    cd ${S}/src/navigation/map-viewer/mapviewercontrol-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=ON -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_TESTS=ON .
    cd ${S}/src/navigation/map-viewer/session-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=ON -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_TESTS=ON .

    cd ${S}/src/navigation/navigation-core && cmake -Dnavit_SRC_DIR=${STAGING_LIBDIR}
    cd ${S}/src/navigation/navigation-core/configuration-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/enhancedposition-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/guidance-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/locationinput-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/mapmatchedposition-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/routing-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .
    cd ${S}/src/navigation/navigation-core/session-plugin && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=OFF -DWITH_REPLAYER=ON -DWITH_IPHONE=OFF -DWITH_TESTS=ON .

    cd ${S}/src/navigation/poi-cam && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/poi-service/poi-server && cmake ${EXTRA_CMAKEFLAGS} -DWITH_DLT=ON -DWITH_GPSD=OFF -DWITH_REPLAYER=ON -DWITH_TESTS=ON .
}

do_compile() {
    cd ${S}/src/navigation/map-viewer && make
    cd ${S}/src/navigation/map-viewer/configuration-plugin && make
    cd ${S}/src/navigation/map-viewer/mapviewercontrol-plugin && make
    cd ${S}/src/navigation/map-viewer/session-plugin && make

    cd ${S}/src/navigation/navigation-core && make
    cd ${S}/src/navigation/navigation-core/configuration-plugin && make
    cd ${S}/src/navigation/navigation-core/enhancedposition-plugin && make
    cd ${S}/src/navigation/navigation-core/guidance-plugin && make
    cd ${S}/src/navigation/navigation-core/locationinput-plugin && make
    cd ${S}/src/navigation/navigation-core/mapmatchedposition-plugin && make
    cd ${S}/src/navigation/navigation-core/routing-plugin && make
    cd ${S}/src/navigation/navigation-core/session-plugin && make
    cd ${S}/src/navigation/poi-cam && make
    cd ${S}/src/poi-service/poi-server && make
}

do_install() {
    install -d ${D}${libdir}/navigation
    find ${S}/src -name libgenivi*.so | xargs -i install -m 0755 {} ${D}${libdir}/navigation
    install -d ${D}${datadir}/navigation-service
#    find ${S}/src -name navit*.xml | xargs -i install -m 0755 {} ${D}${datadir}/navigation-service
    install -m 0755 ${WORKDIR}/navit*.xml ${D}${datadir}/navigation-service
    install -d ${D}${bindir}
    install -m 0755 ${S}/src/poi-service/poi-server/poi-server ${D}${bindir}
    install -d ${D}${includedir}/navigation-service
    install -m 0755 ${S}/api/include/* ${D}${includedir}/navigation-service
    install -m 0755 ${S}/src/poi-service/script/poi-database-sample.db ${D}${datadir}/navigation-service
}

FILES_${PN} += "${libdir}/navigation"
FILES_${PN}-dbg += "${libdir}/navigation/.debug/"
