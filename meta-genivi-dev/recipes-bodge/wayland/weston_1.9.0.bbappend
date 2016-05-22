EXTRA_OECONF_append_vexpressa9 = " WESTON_NATIVE_BACKEND=fbdev-backend.so"

PKG_MESA_qemux86 = "mesa-megadriver "
PKG_MESA_qemux86-64 = "mesa-megadriver "
PKG_MESA_vexpressa9 = "mesa-megadriver "
PKG_MESA ?= " "

RDEPENDS_${PN} += "${PKG_MESA}"
