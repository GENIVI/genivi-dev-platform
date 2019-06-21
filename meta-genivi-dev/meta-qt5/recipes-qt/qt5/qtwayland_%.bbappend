FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

# The recipe providing xcb-proto has changed:
XKB_DEPENDS_remove = "xproto"
XKB_DEPENDS_append = " xorgproto"

SRC_URI_append = "\
    file://0001-Implement-initial-IVI-Shell-support.patch \
    file://0002-add-support-for-IVI-Surface-ID-property.patch \
"

DEPENDS_append_koelsch = " libegl gles-user-module"
DEPENDS_append_rcar-gen3 = " mesa"
