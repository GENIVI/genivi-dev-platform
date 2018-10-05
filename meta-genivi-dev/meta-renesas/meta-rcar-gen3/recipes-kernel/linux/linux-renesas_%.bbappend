FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append += " \
    file://bluetooth.cfg \
    file://rfkill.cfg \
"
