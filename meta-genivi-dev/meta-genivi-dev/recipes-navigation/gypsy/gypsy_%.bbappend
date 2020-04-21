DEPENDS_remove = "bluez4"
DEPENDS_append = " bluez5 libgudev"

PNBLACKLIST[gypsy] = ""

FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"
SRC_URI += "file://gypsy-Wno-unused-function-flag.patch"
