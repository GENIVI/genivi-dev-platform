SUMMARY = "Genivi AudioManager Plugins"
HOMEPAGE = "https://www.genivi.org/"
SECTION = "multimedia"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MPL-2.0;md5=815ca599c9df247a0c7f619bab123dad"

DEPENDS = "audiomanager capicxx-core-native capicxx-dbus-native"

SRCREV = "a0ed3b8f05147e9240d941655488d505057bbae7"
SRC_URI = " \
    git://git.projects.genivi.org/AudioManagerPlugins.git;branch=master;protocol=http \
    file://build-fixup.patch \
    "
S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = " -DWITH_TESTS=OFF \
    -DWITH_COMMAND_INTERFACE_COMMON_CAPI=ON -DWITH_COMMAND_INTERFACE_DBUS=ON \
    -DWITH_ROUTING_INTERFACE_CAPI=ON -DWITH_ROUTING_INTERFACE_DBUS=ON \
    -DWITH_ROUTING_INTERFACE_ASYNC=ON \
    "

FILES_${PN} += " \
    ${libdir}/* \
    /usr/share/* \
    "

do_install_append() {
    mv ${D}${libdir}/audiomanager/* ${D}${libdir}
    rmdir ${D}${libdir}/audiomanager
}
