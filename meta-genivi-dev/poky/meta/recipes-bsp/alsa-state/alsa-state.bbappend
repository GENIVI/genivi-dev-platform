FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append_m3ulcb = "\
  file://m3ulcb.state \
"

do_install_append_m3ulcb() {
  mv ${D}${localstatedir}/lib/alsa/m3ulcb.state ${D}${localstatedir}/lib/alsa/asound.state
}
