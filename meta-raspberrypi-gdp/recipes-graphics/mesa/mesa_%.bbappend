DEPENDS += "python-mako-native libomxil"

PACKAGECONFIG_append = " gallium gallium-llvm"

GALLIUMDRIVERS_append = ",vc4"

PACKAGES =+ "mesa-megadriver-dbg"

FILES_mesa-megadriver-dbg = "${libdir}/dri/.debug/*"
