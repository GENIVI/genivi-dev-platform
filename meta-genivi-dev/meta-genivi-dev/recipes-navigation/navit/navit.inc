DESCRIPTION = "Navit is a car navigation system with routing engine."
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7c0139efe83e40d5f2660a5b3d5847e3 \
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

CONFFILES_${PN}-config += "${datadir}/${PN}/navit.default.xml \
    ${datadir}/${PN}/navit.xml \
    ${datadir}/${PN}/maps.xml \
    ${datadir}/${PN}/osd.xml \
    ${datadir}/${PN}/speech.xml \
    ${datadir}/${PN}/plugins.xml \
"

SRC_URI += "file://navit.xml \
            file://maps.xml \
            file://osd.xml \
            file://speech.xml \
            file://plugins.xml \
            file://switzerland.xml \
"

INSANE_SKIP_${PN} = "dev-so"

FILES_${PN} += "${libdir}/${PN}/*/*.so ${datadir}/icons ${datadir}/${PN}/maps/*"
FILES_${PN}-dev += "${libdir}/${PN}/cmake/navit_macros.cmake \
                    ${datadir}/cmake_plugin_settings.txt \
                   "
FILES_${PN}-dbg += "${libdir}/${PN}/*/.debug ${libdir}/${PN}/*/*/.debug"
FILES_${PN}-maptool = " ${bindir}/maptool "
FILES_${PN}-config = " ${datadir}/${PN}/*.xml "
FILES_${PN}-dbus = " ${datadir}/dbus-1/services/ ${libdir}/${PN}/binding/dbus/libbinding_dbus.so "
FILES_${PN}-font = "${libdir}/${PN}/font/freetype/libfont_freetype.so "
FILES_${PN}-map-binfile = " ${libdir}/${PN}/map/libmap_binfile.so "
FILES_${PN}-map-textfile = " ${libdir}/${PN}/map/libmap_textfile.so "
FILES_${PN}-speech-cmdline = " ${libdir}/${PN}/speech/libspeech_cmdline.so "
FILES_${PN}-speech-dbus = " ${libdir}/${PN}/speech/libspeech_dbus.so "
FILES_${PN}-gui-gtk = " ${libdir}/${PN}/gui/libgui_gtk.so "
FILES_${PN}-gui-qml = " ${libdir}/${PN}/gui/libgui_qml.so ${datadir}/${PN}/skins/ "
FILES_${PN}-gui-internal = " ${libdir}/${PN}/gui/libgui_internal.so "
FILES_${PN}-graphics-sdl = " ${libdir}/${PN}/graphics/libgraphics_sdl.so "
FILES_${PN}-graphics-opengl = " ${libdir}/${PN}/graphics/libgraphics_opengl.so "
FILES_${PN}-graphics-qt = " ${libdir}/${PN}/graphics/libgraphics_qt_qpainter.so "
FILES_${PN}-graphics-gtk = " ${libdir}/${PN}/graphics/libgraphics_gtk_drawing_area.so "
FILES_${PN}-icons = "/usr/share/${PN}/icons/"

EXTRA_OECMAKE = "-DSAMPLE_MAP=n -DDISABLE_QT=1 -Dvehicle/null=1 -Dgraphics/qt_qpainter=0"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${PN}/navit ${D}${bindir}

    install -d ${D}/${libdir}/${PN}/font
    install ${PN}/font/freetype/.libs/libfont_freetype.so ${D}/${libdir}/${PN}/font

    install -d ${D}/${libdir}/${PN}/graphics
    install ${PN}/graphics/null/.libs/libgraphics_null.so ${D}/${libdir}/${PN}/graphics
    install ${PN}/graphics/opengl/.libs/libgraphics_opengl.so ${D}/${libdir}/${PN}/graphics

    install -d ${D}/${libdir}/${PN}/gui
    install ${PN}/gui/internal/.libs/libgui_internal.so ${D}/${libdir}/${PN}/gui

    install -d ${D}/${libdir}/${PN}/speech
    install ${PN}/speech/cmdline/.libs/libspeech_cmdline.so ${D}/${libdir}/${PN}/speech

    install -d ${D}/${libdir}/${PN}/map
    install ${PN}/map/binfile/.libs/libmap_binfile.so ${D}/${libdir}/${PN}/map
    install ${PN}/map/textfile/.libs/libmap_textfile.so ${D}/${libdir}/${PN}/map

    install -d ${D}/${libdir}/${PN}/osd
    install ${PN}/osd/core/.libs/libosd_core.so ${D}/${libdir}/${PN}/osd

    install -d ${D}/${libdir}/${PN}/vehicle
    install ${PN}/vehicle/demo/.libs/libvehicle_demo.so ${D}/${libdir}/${PN}/vehicle
    install ${PN}/vehicle/file/.libs/libvehicle_file.so ${D}/${libdir}/${PN}/vehicle
    install ${PN}/vehicle/gpsd_dbus/.libs/libvehicle_gpsd_dbus.so ${D}/${libdir}/${PN}/vehicle
    install ${PN}/vehicle/gypsy/.libs/libvehicle_gypsy.so ${D}/${libdir}/${PN}/vehicle
    install ${PN}/vehicle/null/.libs/libvehicle_null.so ${D}/${libdir}/${PN}/vehicle

    install -d ${D}${includedir}/${PN}
    install -m 0755 ${B}/config.h ${D}${includedir}
    install -m 0755 ${S}/${PN}/*.h ${D}${includedir}/${PN}

    install -d ${D}${libdir}/${PN}/cmake
    install -m 0755 ${S}/cmake/navit_macros.cmake ${D}${libdir}/${PN}/cmake
    install -d ${D}${datadir}
    install -m 0755 ${B}/cmake_plugin_settings.txt ${D}${datadir}

    install -d ${D}${libdir}/${PN}/navit
    install -m 0755 ${S}/${PN}/navit_shipped.xml ${D}${libdir}/${PN}/navit/

    install -d ${D}/${libdir}/${PN}/binding/dbus
    install ${B}/${PN}/binding/dbus/.libs/libbinding_dbus.so ${D}/${libdir}/${PN}/binding/dbus

    # Put all the xml in /usr/share/navit and then only switzerland.xml in maps
    # Take care of this else infinite loop !
    install -d ${D}${datadir}/${PN}/maps
    install -m 0755 ${WORKDIR}/*.xml ${D}${datadir}/${PN}
    mv ${D}${datadir}/${PN}/switzerland.xml ${D}${datadir}/${PN}/maps

    install -d ${D}/${datadir}/${PN}/icons
    install ${B}/${PN}/icons/*.png ${D}/${datadir}/${PN}/icons
}

python do_package_prepend () {
    d.setVar('ALLOW_EMPTY', '1')
}
