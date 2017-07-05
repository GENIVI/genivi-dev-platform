FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "\
    file://0001-Implement-initial-IVI-Shell-support.patch \
    file://0002-add-support-for-IVI-Surface-ID-property.patch \
"

DEPENDS_append_koelsch = " libegl gles-user-module"
