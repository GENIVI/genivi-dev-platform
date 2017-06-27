# Copyright (C) 2015 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "\
    file://EGLWLInputEventExample.service \
    file://EGLWLMockNavigation.service \
    "

FILES_${PN} += "\
    ${libdir}/systemd/user/* \
    "

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/EGLWLInputEventExample.service \
	                ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/EGLWLMockNavigation.service \
	                ${D}${libdir}/systemd/user
}
