DEPENDS += "python-mako-native libomxil"

SRCREV = "1762568fd39b9be42d963d335e36daea25df7044"
PV="11.0-git${SRCPV}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI = "\
    git://anongit.freedesktop.org/mesa/mesa.git;branch=11.0 \
    file://nir_include_dirs.patch \ 
"

PACKAGECONFIG_append = " gallium gallium-llvm"

GALLIUMDRIVERS_append_raspberrypi2 = ",vc4"
