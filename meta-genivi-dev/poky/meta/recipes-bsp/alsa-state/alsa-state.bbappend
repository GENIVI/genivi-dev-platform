FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_prepend_m3ulcb = "\
  file://asound.state \
"
