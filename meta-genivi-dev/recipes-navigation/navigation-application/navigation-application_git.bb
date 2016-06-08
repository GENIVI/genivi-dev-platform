SUMMARY = "Navigation application (with HMI) of the GENIVI navigation project"
DESCRIPTION = "Navigation application based on Navit and compliant with the \
Navigation APIs standardized by the GENIVI Alliance. The GENIVI APIs are \
implemented into Navit plugins, running on DBus. The HMI is made in QML."
HOMEPAGE = "http://projects.genivi.org/ivi-navigation"

PV = "1"

DEPENDS = "automotive-message-broker navit navigation-service qtbase qtdeclarative"
RDEPENDS_${PN} = "qtquickcontrols-qmlplugins"

SRC_URI = "git://github.com/GENIVI/navigation-application.git;protocol=http \
           file://remove_amb_link_path.patch \
           file://pregenerated_images_and_style_sheets_1024x768.tar.bz2 \
           file://allow_qt_moc_from_build_system.patch \
           file://change_constants_path_ressourcejs.patch \
           file://constants.js \
           file://launch_gdp_fsa.sh \
           file://kill_gdp_fsa.sh \
           file://0001-Fix-reduce-relocations-error.patch \
          "
SRCREV = "5c53c08a85ac2f953f986cf21023a938fb77c385"

S = "${WORKDIR}/git"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2e0f5070190199ece7f69c3c14e2e7af"

inherit autotools cmake

EXTRA_CMAKEFLAGS = "-DDBUS_GENERATED_INCLUDE_DIR_PARENT_SCOPE=${STAGING_INCDIR}"

PARALLEL_MAKE = ""

do_configure() {
    cd ${S}/src/genivilogreplayer && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/fuel-stop-advisor && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/hmi/qml/hmi-launcher && cmake ${EXTRA_CMAKEFLAGS} -DQT_MOC=${STAGING_BINDIR_NATIVE}/qt5/moc .
}

do_compile() {
    cd ${S}/src/genivilogreplayer && make
    cd ${S}/src/fuel-stop-advisor && make
    cd ${S}/src/hmi/qml/hmi-launcher && make
}

do_install() {
    install -d ${D}${bindir}/navigation-application/qml
    install -m 0755 ${S}/src/fuel-stop-advisor/fuel-stop-advisor ${D}${bindir}
    install -m 0755 ${S}/src/hmi/qml/hmi-launcher/hmi-launcher ${D}${bindir}
    install -m 0755 ${WORKDIR}/launch_gdp_fsa.sh ${D}${bindir}
    install -m 0755 ${WORKDIR}/kill_gdp_fsa.sh ${D}${bindir}
    install -m 0755 ${S}/src/hmi/qml/*.qml ${S}/src/hmi/qml/FSA.qmlproject ${D}/${bindir}/navigation-application/qml
    cp -r ${S}/src/hmi/qml/Core ${D}/${bindir}/navigation-application/qml

    install -d ${D}${libdir}/navigation
    install -m 0755 ${S}/src/genivilogreplayer/genivilogreplayerplugin.so ${D}${libdir}/navigation

    install -d ${D}${sysconfdir}/ambd/
    install -m 0755 ${S}//src/genivilogreplayer/logreplayerconfig.in.json ${D}${sysconfdir}/ambd/
    sed -i 's/..\/..\/..\/build\/genivilogreplayer\//\/usr\/lib\/navigation\//' ${D}${sysconfdir}/ambd/logreplayerconfig.in.json
    sed -i 's/plugins\/dbus\//\/usr\/lib\/automotive-message-broker\//' ${D}${sysconfdir}/ambd/logreplayerconfig.in.json

    cp -r ${WORKDIR}/pregenerated_files/images ${WORKDIR}/pregenerated_files/style-sheets ${D}${bindir}/navigation-application/qml/Core

    cp ${WORKDIR}/constants.js ${D}${bindir}/navigation-application/qml/Core
    sed -i 's/..\/..\/..\/build\/hmi\/qml\/hmi-launcher\///' ${D}${bindir}/navigation-application/qml/Core/genivi.js

}

FILES_${PN} += "${libdir}/navigation/genivilogreplayerplugin.so"
FILES_${PN}-dbg += "${libdir}/navigation/.debug/"

INSANE_SKIP_${PN} = "dev-so"
