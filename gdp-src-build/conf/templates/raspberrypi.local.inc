include local.inc

GPU_MEM = "128"

CORE_IMAGE_EXTRA_INSTALL += "wayland weston"

MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

IMAGE_CLASSES = "sdcard_image-rpi-gdp"

KERNEL_DEVICETREE_append = " overlays/vc4-kms-v3d-overlay.dtb"

PREFERRED_VERSION_linux-raspberrypi = "4.4.%"
PREFERRED_VERSION_mesa = "11.%"

PREFERRED_PROVIDER_virtual/egl = "mesa"
PREFERRED_PROVIDER_virtual/libgles2 = "mesa"
PREFERRED_PROVIDER_virtual/libgl = "mesa"
PREFERRED_PROVIDER_virtual/mesa = "mesa"
PREFERRED_PROVIDER_jpeg = "jpeg"
