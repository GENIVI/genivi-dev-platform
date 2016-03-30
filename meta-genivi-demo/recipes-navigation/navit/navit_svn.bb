require navit.inc

SRCREV = "5532"
PV = "0.2.0+svnr${SRCPV}"
PR = "${INC_PR}.3"

S = "${WORKDIR}/${PN}"
SRC_URI += "svn://anonymous@navit.svn.sourceforge.net/svnroot/navit/trunk;module=navit;protocol=http \
"
SRC_URI += "file://0001-add-wayland-ilm-support-navit.patch"
