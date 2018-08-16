include local.inc

# vc4grahpics do not use GPU_MEM, instead it uses the CMA memory, allocating
# a high number of GPU_MEM does seem to eat memory from CMA causing OOM errors.
#
# Ultimatly we would like to set this to 0, but it does seem to need a small
# amount otherwise the system will not boot. Fails at first stage already,
# loading the Linux kernel.
GPU_MEM = "16"

MACHINE_FEATURES_append = " vc4graphics"

MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

CUSTOM_IMAGE_FSTYPE ?= "wic.gz"

require conf/distro/sota.conf.inc

# If credentials are not provided to the aktualizr application it will fail
# to start and the systemd service will keep retrying, polluting our logs.
#
# aktualizr is disabled by default for this reason.
#
# To activate, provide the appropriate credentials in SOTA_PACKED_CREDENTIALS
# and comment/remove the line that disables the aktualizr systemd service.
#
# Details about this can be found here:
#     https://github.com/advancedtelematic/meta-updater#sota-related-variables-in-local-conf

#SOTA_PACKED_CREDENTIALS = "<path to credentials>"
SYSTEMD_AUTO_ENABLE_pn-aktualizr = "disable"

OSTREE_KERNEL_ARGS_sota = " cma=256M 8250.nr_uarts=1 bcm2708_fb.fbwidth=720 bcm2708_fb.fbheight=480 bcm2708_fb.fbswap=1 vc_mem.mem_base=0x3ec00000 vc_mem.mem_size=0x40000000 dwc_otg.lpm_enable=0 console=ttyS0,115200 usbhid.mousepoll=0 "
