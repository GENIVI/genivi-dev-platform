SUMMARY = "Library to handle input devices in Wayland compositors"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/libinput/"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=2184aef38ff137ed33ce9a63b9d1eb8f"

DEPENDS = "libevdev udev mtdev"

SRC_URI = "http://www.freedesktop.org/software/${BPN}/${BP}.tar.xz \
"
SRC_URI[md5sum] = "4a3fc842ac0b7bf0fa306ea7badc31a0"
SRC_URI[sha256sum] = "2de2b433ddc73ba1bfd4d309fd873d0ced6937cf1a7a94e4afbf18ddd8f8dd17"

inherit autotools pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[libunwind] = "--with-libunwind,--without-libunwind,libunwind"
PACKAGECONFIG[gui] = "--enable-event-gui,--disable-event-gui,cairo gtk+3"

FILES_${PN} += "${libdir}/udev/"
FILES_${PN}-dbg += "${libdir}/udev/.debug"
