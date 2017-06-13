FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://0003-Workaround-crashes-in-QtQml-code-related-to-dead-sto.patch"
