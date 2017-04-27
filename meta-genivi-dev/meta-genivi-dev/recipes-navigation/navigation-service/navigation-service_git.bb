SUMMARY = "Navigation software based on Navit and compliant with the Navigation APIs standardized by the GENIVI Alliance"
SRCREV = "b9bda20d81ea44ed8265de8819eb95e2e31dabf9"
PV = "1"

SRC_URI = "git://github.com/GENIVI/navigation.git;protocol=http \
           file://change_directories_xsl_files.patch \
          "

DEPENDS = "wayland-ivi-extension navit positioning libxslt-native dbus-c++-native"

S = "${WORKDIR}/git"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://src/navigation/LICENSE;md5=50772b2cd18ba00801e433556c1924bc"

inherit cmake

EXTRA_CMAKEFLAGS = " -DYOCTO_CONFIG=ON \
                     -DSTAGING_INCDIR=${STAGING_INCDIR} \
                     -DSTAGING_LIBDIR=${STAGING_LIBDIR} \
                     -DSTAGING_DATADIR=${STAGING_DATADIR} \
                     -DWITH_DEBUG=ON \
                   "

do_configure() {
    cd ${S}/api/map-viewer && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/navigation && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/poi-service && cmake ${EXTRA_CMAKEFLAGS} .
}

do_compile() {
    cd ${S}/src/navigation && make
    cd ${S}/src/poi-service && make
}

do_install() {
    sed -i '/libgraphics_null.so/a\\t\t<plugin path="$NAVIT_LIBDIR/*/${NAVIT_LIBPREFIX}libgraphics_opengl.so"/>' ${S}/src/navigation/map-viewer/navit_genivi_mapviewer.xml

    install -d ${D}${libdir}/navigation
    find ${S}/src -name libgenivi*.so | xargs -i install -m 0755 {} ${D}${libdir}/navigation
    install -d ${D}${datadir}/navigation-service
    find ${S}/src -name navit*.xml | xargs -i install -m 0755 {} ${D}${datadir}/navigation-service
    install -d ${D}${bindir}
    install -m 0755 ${S}/src/poi-service/bin/poi-server ${D}${bindir}
    install -d ${D}${includedir}/navigation-core
    install -m 0755 ${S}/src/navigation/dbus-include/*/*.h ${D}${includedir}/navigation-core

    install -m 0755 ${S}/src/poi-service/resource/empty.db ${D}${datadir}/navigation-service

    install -d 0755 ${D}${datadir}/navit/maps
    #For switzerland
    cp ${S}/src/navigation/map/switzerland.bin ${D}${datadir}/navit/maps

    install -d ${D}${libdir}/systemd/user/
    install -m 0644 ${S}/src/navigation/map-viewer/navit_genivi_mapviewer_fsa.service ${D}${libdir}/systemd/user

    install -d ${D}${libdir}/systemd/user/
    install -m 0644 ${S}/src/navigation/navigation-core/navit_genivi_navigationcore_fsa.service ${D}${libdir}/systemd/user

    install -d ${D}${libdir}/systemd/user/
    install -m 0644 ${S}/src/poi-service/poi-server/poi-server.service ${D}/${libdir}/systemd/user

}

FILES_${PN} += "${libdir}/navigation"
FILES_${PN} += "${datadir}/navit/maps"
FILES_${PN} += "${libdir}/systemd/user/*.service"
FILES_${PN}-dbg += "${libdir}/navigation/.debug/"
