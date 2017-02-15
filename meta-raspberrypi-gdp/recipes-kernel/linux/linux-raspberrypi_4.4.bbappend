FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

LINUX_VERSION = "4.4.16"

SRCREV = "26550dcfb86b0308a99f726abbfb55abb1b0f78c"

SRC_URI_append = " file://0001-rpi-defconfig.patch"

CMDLINE_append = " usbhid.mousepoll=0"

KERNEL_MODULE_AUTOLOAD += "snd-bcm2835"
KERNEL_MODULE_AUTOLOAD += "hid-multitouch"

RDEPENDS_${PN} += "kernel-module-snd-bcm2835"
PACKAGES += "kernel-module-snd-bcm2835"
