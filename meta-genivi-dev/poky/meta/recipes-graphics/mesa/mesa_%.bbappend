FILESEXTRAPATHS_prepend_qemux86-64 := "${THISDIR}/${BPN}:"
SRC_URI_append_qemux86-64 = " file://0001-Use-llvm_prefix-variable-directly.patch"

EXTRA_OECONF_qemux86-64 = "--enable-shared-glapi --with-llvm-prefix=${STAGING_BINDIR_CROSS}"

PACKAGECONFIG_append_qemux86-64 = " gallium gallium-llvm"

GALLIUMDRIVERS_append_qemux86-64 = ",virgl"
