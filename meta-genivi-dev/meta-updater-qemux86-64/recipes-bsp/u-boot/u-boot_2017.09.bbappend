FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_qemuall = " file://0001-add-ip-kernel-argument.patch"
