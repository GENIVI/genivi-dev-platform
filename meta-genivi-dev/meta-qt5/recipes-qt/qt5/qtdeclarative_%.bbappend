FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append_aarch64 = "\
    file://0001-V4-Free-up-2-address-bits-in-64bit-mode.patch \
    file://0002-V4-Fix-usage-of-QV4-Value-tags-types.patch \
"
