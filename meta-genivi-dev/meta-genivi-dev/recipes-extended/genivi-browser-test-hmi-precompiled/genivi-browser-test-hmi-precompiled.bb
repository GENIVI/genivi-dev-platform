SUMMARY = "GENIVI Browser Framework TestHMI"
DESCRIPTION = "This is the official source of the GENIVI Browser Framework TestHMI."
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=815ca599c9df247a0c7f619bab123dad"
PR = "r0"

RDEPENDS_${PN} = "python-pyqt"

SRC_URI = "file://genivi-browser-test.tar.bz2"

S = "${WORKDIR}/genivi-browser-test"

do_install() {
	# Copy binary into sysroot
	mkdir -p ${D}/opt/genivi
	cp -r ${S}/src ${D}/opt/genivi
	cp -r ${S}/run.sh ${D}/opt/genivi
}

FILES_${PN} = " \
	/opt/genivi/* \
"
