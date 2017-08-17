FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qemux86-64 = "\
        file://drm.cfg \
"

SRC_URI_append += " \
    file://bluetooth.cfg \
    file://rfkill.cfg \
"
