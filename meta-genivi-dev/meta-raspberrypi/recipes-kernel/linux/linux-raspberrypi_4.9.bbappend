FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-arm-rpi-vc4-kms-overlay-applied-manually.patch"

CMDLINE_append = " usbhid.mousepoll=0"

KERNEL_MODULE_AUTOLOAD += "snd-bcm2835"
KERNEL_MODULE_AUTOLOAD += "hid-multitouch"

RDEPENDS_${PN} += "kernel-module-snd-bcm2835"
PACKAGES += "kernel-module-snd-bcm2835"
