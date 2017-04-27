SUMMARY = "Navigation application (with HMI) of the GENIVI navigation project"
DESCRIPTION = "Navigation application based on Navit and compliant with the \
Navigation APIs standardized by the GENIVI Alliance. The GENIVI APIs are \
implemented into Navit plugins, running on DBus. The HMI is made in QML."
HOMEPAGE = "http://projects.genivi.org/ivi-navigation"

PV = "1"

DEPENDS = "dbus-c++-native libxslt-native qttools-native qtbase-native \
           automotive-message-broker navit navigation-service qtbase \
           qtdeclarative qtquickcontrols wayland-ivi-extension"

SRC_URI = "git://github.com/GENIVI/navigation-application.git;protocol=http \
           file://remove_amb_link_path.patch \
           file://change_constants_path_ressourcejs.patch \
           file://constants.js \
           file://add_include_directory_for_dlt_log.patch \
           file://ambd_conn_from_session_to_system.patch \
          "
SRCREV = "5a18639081b948758def95b7cc34156f5e7fbd88"

S = "${WORKDIR}/git"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2e0f5070190199ece7f69c3c14e2e7af"

inherit autotools cmake cmake_qt5

EXTRA_CMAKEFLAGS = "-DDBUS_GENERATED_INCLUDE_DIR=${STAGING_INCDIR} -DTRIPCOMPUTER_DIR=${S}/src/tripcomputer --DWITH_DEBUG=ON"

PARALLEL_MAKE = ""

do_configure() {
    cd ${S}/src/genivilogreplayer && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/fuel-stop-advisor && cmake ${EXTRA_CMAKEFLAGS} .
    cd ${S}/src/hmi/hmi-launcher && cmake ${EXTRA_CMAKEFLAGS} -DQT_MOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/qt5/moc -DQT_LRELEASE=${STAGING_BINDIR_NATIVE}/qt5/lrelease -DQT_LUPDATE=${STAGING_BINDIR_NATIVE}/qt5/lupdate .
}

do_compile() {
    cd ${S}/src/genivilogreplayer && make
    cd ${S}/src/fuel-stop-advisor && make
    cd ${S}/src/hmi/hmi-launcher && make
}

do_install() {
    install -d ${D}${bindir}/navigation-application/qml
    install -m 0755 ${S}/src/fuel-stop-advisor/fuel-stop-advisor ${D}${bindir}
    install -m 0755 ${S}/src/hmi/hmi-launcher/hmi-launcher ${D}${bindir}
    cp ${S}/src/hmi/hmi-launcher/*.qm ${D}${bindir}
    install -m 0755 ${S}/src/hmi/qml/*.qml ${S}/src/hmi/qml/FSA.qmlproject ${D}/${bindir}/navigation-application/qml
    cp -r ${S}/src/hmi/qml/Core ${D}/${bindir}/navigation-application/qml

    install -d ${D}${libdir}/navigation
    install -m 0755 ${S}/src/genivilogreplayer/genivilogreplayerplugin.so ${D}${libdir}/navigation

    install -d ${D}${sysconfdir}/ambd/
    install -m 0755 ${S}//src/genivilogreplayer/logreplayerconfig.in.json ${D}${sysconfdir}/ambd/
    sed -i 's/..\/..\/..\/build\/genivilogreplayer\//\/usr\/lib\/navigation\//' ${D}${sysconfdir}/ambd/logreplayerconfig.in.json
    sed -i 's/plugins\/dbus\//\/usr\/lib\/automotive-message-broker\//' ${D}${sysconfdir}/ambd/logreplayerconfig.in.json

    tar -xzf ${S}/src/referenceHMI.tar.gz -C ${D}${bindir}/navigation-application --strip 2

    cp ${WORKDIR}/constants.js ${D}${bindir}/navigation-application/qml/Core
    sed -i 's/..\/..\/..\/build\/hmi\/hmi-launcher\///' ${D}${bindir}/navigation-application/qml/Core/genivi-origin.js
    cd ${D}${bindir}/navigation-application/qml/Core/
    ln -s genivi-origin.js genivi.js

    install -d ${D}/${libdir}/systemd/user/
    install -m 0644 ${S}/src/fuel-stop-advisor/fuel_stop_advisor.service ${D}/${libdir}/systemd/user

     install -d ${D}/${libdir}/systemd/user/
     install -m 0444 ${S}/src/hmi/hmi-launcher/hmi-launcher_fsa.service ${D}/${libdir}/systemd/user/hmi-launcher_fsa.service

     cp ${S}/src/hmi/config/*.conf ${D}${bindir}/navigation-application
     #For switzerland
     mkdir -p ${D}/home/root/.config/navigation
     cp ${D}${bindir}/navigation-application/fsa_switzerland.conf ${D}/home/root/.config/navigation/fsa.conf
}

FILES_${PN} += "${libdir}/navigation/genivilogreplayerplugin.so"
FILES_${PN} += "${libdir}/systemd/user/*.service"
FILES_${PN}-dbg += "${libdir}/navigation/.debug/"
FILES_${PN} += "${datadir}/navigation-service"
FILES_${PN} += "/home/root/.config/navigation/fsa.conf"

INSANE_SKIP_${PN} = "dev-so"
