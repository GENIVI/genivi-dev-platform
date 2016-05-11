FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = "\
	file://0001-rpi2-defconfig.patch \
	${@base_conditional('USE_FAYTECH_MONITOR', '1', 'file://0002-faytech-fix-rpi.patch', '', d)} \
"

CMDLINE_append = " usbhid.mousepoll=0"

KERNEL_MODULE_AUTOLOAD += "snd-bcm2835"

RDEPENDS_${PN} += "kernel-module-snd-bcm2835"
PACKAGES += "kernel-module-snd-bcm2835"
