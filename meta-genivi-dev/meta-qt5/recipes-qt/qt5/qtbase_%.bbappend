FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS_append_koelsch = " libegl"

PACKAGECONFIG_GL = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"
PACKAGECONFIG_append = " icu accessibility eglfs kms"
PACKAGECONFIG_append_rpi = " fontconfig"

EXTRA_QMAKEVARS_PRE_append = " INCLUDEPATH+=${STAGING_INCDIR}/drm"
EXTRA_OECONF_append = " -qpa wayland"

# Temporarily fix bug due to binutils 2.28
QT_CONFIG_FLAGS_append = " -no-use-gold-linker"
