FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

USE_FAYTECH_MONITOR ?= "0"
SRC_URI_append_porter = " \
    ${@' file://0001-faytech-fix-porter.patch' \
    if ${USE_FAYTECH_MONITOR} == 1 else ''} \
"

SRC_URI_append_raspberrypi2 = " \
    ${@' file://0001-faytech-fix-rpi.patch' \
    if ${USE_FAYTECH_MONITOR} == 1 else ''} \
"

