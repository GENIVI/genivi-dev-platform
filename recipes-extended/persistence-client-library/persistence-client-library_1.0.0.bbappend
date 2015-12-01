FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "\
    file://0001-libpers_common.so-extension-fix-for-EGL.patch \
    "
