FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = " file://systemd-user-enable-optional-pam_systemd.so-session.patch"
