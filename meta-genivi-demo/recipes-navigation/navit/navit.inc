DESCRIPTION = "Navit is a car navigation system with routing engine."
LICENSE = "GPLv2 LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=ed539d099d6ce08de6ea0dfed9ecb333 \
                    file://LGPL-2;md5=3214f080875748938ba060314b4f727d \
                    file://GPL-2;md5=751419260aa954499f7abaabaa882bbe \
"

DEPENDS = "glib-2.0 gtk+3 gd gypsy librsvg-native virtual/libgl"
RDEPENDS_${PN} = " ${PN}-icons ${PN}-config "
RRECOMMENDS_${PN} = "gpsd ${PN}-dbus ${PN}-speech-cmdline ${PN}-gui-internal ${PN}-graphics-gtk"
RSUGGESTS_${PN} = "flite-alsa espeak ${PN}-speech-dbus ${PN}-gui-gtk ${PN}-gui-qml ${PN}-graphics-sdl ${PN}-graphics-qt ${PN}-maptool"

DEPENDS += "freetype"
PE = "1"
INC_PR = "r9"

inherit autotools gettext cmake

#check for TARGET_FPU=soft and inform configure of the result so it can disable some floating points
require navit-fpu.inc
EXTRA_OECONF += "${@get_navit_fpu_setting(bb, d)}"

PACKAGES =+ "${PN}-maptool ${PN}-config ${PN}-dbus ${PN}-speech-cmdline ${PN}-speech-dbus ${PN}-gui-gtk ${PN}-gui-internal ${PN}-gui-qml ${PN}-graphics-sdl ${PN}-graphics-gtk ${PN}-graphics-qt ${PN}-icons"

EXTRA_AUTORECONF = " -I m4"

CONFFILES_${PN}-config += "${datadir}/navit/navit.default.xml \
    ${datadir}/navit/navit.xml \
    ${datadir}/navit/maps.xml \
    ${datadir}/navit/osd.xml \
    ${datadir}/navit/speech.xml \
    ${datadir}/navit/plugins.xml \
"

SRC_URI += "file://navit.xml \
            file://maps.xml \
            file://osd.xml \
            file://speech.xml \
            file://plugins.xml \
            file://switzerland.xml \
"

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/${PN}/*/*.so ${datadir}/icons ${datadir}/navit/maps/*"
FILES_${PN}-dev += "${libdir}/navit/cmake/navit_macros.cmake \
                    ${datadir}/cmake_plugin_settings.txt \
                   "
FILES_${PN}-dbg += "${libdir}/${PN}/*/.debug ${libdir}/${PN}/*/*/.debug"
FILES_${PN}-maptool = " ${bindir}/maptool "
FILES_${PN}-config = " ${datadir}/navit/*.xml "
FILES_${PN}-dbus = " ${datadir}/dbus-1/services/ ${libdir}/${PN}/binding/dbus/libbinding_dbus.so "
FILES_${PN}-speech-cmdline = " ${libdir}/${PN}/speech/libspeech_cmdline.so "
FILES_${PN}-speech-dbus = " ${libdir}/${PN}/speech/libspeech_dbus.so "
FILES_${PN}-gui-gtk = " ${libdir}/${PN}/gui/libgui_gtk.so "
FILES_${PN}-gui-qml = " ${libdir}/${PN}/gui/libgui_qml.so ${datadir}/navit/skins/ "
FILES_${PN}-gui-internal = " ${libdir}/${PN}/gui/libgui_internal.so "
FILES_${PN}-graphics-sdl = " ${libdir}/${PN}/graphics/libgraphics_sdl.so "
FILES_${PN}-graphics-opengl = " ${libdir}/${PN}/graphics/libgraphics_opengl.so "
FILES_${PN}-graphics-qt = " ${libdir}/${PN}/graphics/libgraphics_qt_qpainter.so "
FILES_${PN}-graphics-gtk = " ${libdir}/${PN}/graphics/libgraphics_gtk_drawing_area.so "
FILES_${PN}-icons = "/usr/share/navit/xpm/"

do_configure_prepend() {
    # MOC=`$PKG_CONFIG QtGui --variable=moc_location` returns only /usr/bin/moc4 which is not available on hosts without Qt
    sed -i "s#MOC=#MOC=${STAGING_DIR_NATIVE}#g" ${S}/configure.in
}

EXTRA_OECMAKE = "-DSAMPLE_MAP=n -DDISABLE_QT=1 -Dvehicle/null=1 -Dgraphics/qt_qpainter=0"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${PN}/navit ${D}${bindir}

    install -d ${D}/${libdir}/${PN}/graphics
    install ${PN}/graphics/null/.libs/libgraphics_null.so ${D}/${libdir}/${PN}/graphics
    install ${PN}/graphics/opengl/.libs/libgraphics_opengl.so ${D}/${libdir}/${PN}/graphics

    install -d ${D}${includedir}/navit
    install -m 0755 ${B}/config.h ${D}${includedir}/navit
    install -m 0755 ${S}/navit/*.h ${D}${includedir}/navit

    install -d ${D}${libdir}/navit/cmake
    install -m 0755 ${S}/cmake/navit_macros.cmake ${D}${libdir}/navit/cmake
    install -d ${D}${datadir}
    install -m 0755 ${B}/cmake_plugin_settings.txt ${D}${datadir}

    install -d ${D}${libdir}/navit/navit
    install -m 0755 ${S}/navit/navit_shipped.xml ${D}${libdir}/navit/navit/

    install -d ${D}/${libdir}/${PN}/binding/dbus
    install ${B}/navit/binding/dbus/.libs/libbinding_dbus.so ${D}/${libdir}/${PN}/binding/dbus

    install -d ${D}${datadir}/navit/maps
    install -m 0755 ${WORKDIR}/*.xml ${D}${datadir}/navit
    mv ${D}${datadir}/navit/switzerland.xml ${D}${datadir}/navit/maps
}

python do_package_prepend () {
    d.setVar('ALLOW_EMPTY', '1')
}
