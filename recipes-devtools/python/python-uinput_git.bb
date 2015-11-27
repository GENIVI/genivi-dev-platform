SUMMARY = "uinput binding for python"
SECTION = "devel/python"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"
DEPENDS = "python udev"
RDEPENDS_${PN} = "python-core python-ctypes python-distutils"

SRC_URI = "git://github.com/tuomasjjrasanen/python-uinput;branch=master;protocol=git"
SRCREV = "1c37e30e57d005d56630048505f79f8202e14dd0"

S = "${WORKDIR}/git"

inherit python-dir setuptools


# do_install() {
#     install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/
#     install -m 0755 libsip.so.1.0.0 ${D}${libdir}/${PYTHON_DIR}/site-packages/sip.so
#     # sipconfig.py sipdistutils.py
#     install -d ${D}${includedir}
#     install -m 0644 ${S}/sip.h ${D}${includedir}/sip.h
# }
# 
# FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages/sip.so"
# 
# FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/.debug/*"
