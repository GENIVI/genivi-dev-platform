DESCRIPTION = "ALSA Plugins"
HOMEPAGE = "http://www.alsa-project.org"
SECTION = "multimedia/alsa/plugins"
LICENSE = "LGPL2.1"
DEPENDS = "alsa-lib pulseaudio"
PR = "r1"

LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"
SRC_URI = "ftp://ftp.alsa-project.org/pub/plugins/alsa-plugins-${PV}.tar.bz2 \
"

SRC_URI[md5sum] = "6fcbbb31e96f8ebc5fb926184a717aa4"
SRC_URI[sha256sum] = "426f8af1a07ee9d8c06449524d1f0bd59a06e0331a51aa3d59d343a7c6d03120"

inherit autotools pkgconfig

PACKAGES_DYNAMIC = "libasound-module*"

python populate_packages_prepend() {
    plugindir = bb.data.expand('${libdir}/alsa-lib/', d)
    do_split_packages(d, plugindir, '^libasound_module_(.*)\.so$', 'libasound-module-%s', 'Alsa plugin for %s', extra_depends='' )
}

FILES_${PN}-dev += "${libdir}/alsa-lib/libasound*.a ${libdir}/alsa-lib/libasound*.la"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug"

FILES_libasound-module-conf-pulse += "${datadir}/alsa/alsa.conf.d/*pulseaudio*"
FILES_libasound-module-rate-samplerate += "${libdir}/alsa-lib/*rate_samplerate_*.so"
FILES_libasound-module-rate-speexrate += "${libdir}/alsa-lib/*rate_speexrate_*.so"

# Symlinks to .so files cause a warning, but these are legitimate.
INSANE_SKIP_libasound-module-rate-samplerate = "dev-so"
INSANE_SKIP_libasound-module-rate-speexrate = "dev-so"
