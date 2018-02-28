# Copyright (C) 2017 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

INPUT_EXAMPLE_DESKTOP_FILE="EGLWLInputEventExample.desktop"
MOCK_NAVIGATION_DESKTOP_FILE="EGLWLMockNavigation.desktop"

SRC_URI_append = "                         \
    file://${INPUT_EXAMPLE_DESKTOP_FILE}   \
    file://${MOCK_NAVIGATION_DESKTOP_FILE} \
    file://0001-examples-print.cpp-include-stdlib.h-for-free.patch \
    "

FILES_${PN} += "\
    /usr/share/applications/* \
    "

do_install_append() {
  install -Dm 0644 ${WORKDIR}/${INPUT_EXAMPLE_DESKTOP_FILE}   \
                   ${D}/usr/share/applications/${INPUT_EXAMPLE_DESKTOP_FILE}

  install -Dm 0644 ${WORKDIR}/${MOCK_NAVIGATION_DESKTOP_FILE} \
                   ${D}/usr/share/applications/${MOCK_NAVIGATION_DESKTOP_FILE}
}

