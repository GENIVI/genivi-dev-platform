FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append_rpi = "\
    file://0001-Correct-label-on-RPI.patch \
    file://0002-Correct-textfield-on-RPI.patch \
"
