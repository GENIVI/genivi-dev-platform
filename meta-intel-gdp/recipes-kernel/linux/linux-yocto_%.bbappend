FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

USE_FAYTECH_MONITOR ?= "0"

SRC_URI_append = " \
    file://hidmultitouch.cfg \
    ${@' file://0001-faytech-fix-minnow.patch' \
    if '${USE_FAYTECH_MONITOR}' == '1' else ''} \
"
