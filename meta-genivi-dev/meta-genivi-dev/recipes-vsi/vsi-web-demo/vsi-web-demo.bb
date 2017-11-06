SUMMARY = "Vehicle Signal Interface demo"
DESCRIPTION = "A demo using the Vehicle Signal Interface"
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

HOMEPAGE = "https://github.com/GENIVI/vehicle_signal_interface"
SECTION = "base"

SRC_URI = " \
    git://github.com/GENIVI/vsi_web_demo.git \
    file://vsi-web-demo.service \
"
SRCREV = "268f69986fdc51cd5dc75fb4b75539f547f7b5bc"
LICENSE = "MPL-2.0 & MIT"
LIC_FILE_CHKSUM = "file://${S}/LICENSE;md5=a01101688b9153ab405f6fb02a5f2d68"

inherit systemd

# These are actual runtime dependencies by the VSI demo
RDEPENDS_${PN} = "python3-multiprocessing kernel-module-vcan"

# These are needed to build/install packages using pip3 to get crossbar
# installed on the target
RDEPENDS_${PN} += "python3-pip gcc gcc-symlinks binutils"

# These commands are needed to install crossbar and getting the vsi-web-demo to
# run on the target.
# pip3 install --ignore-installed setuptools
# pip3 install crossbar asyncio 
# ln -s /lib/ /lib64

SYSTEMD_SERVICE_${PN} = " vsi-web-demo.service"

SYSTEMD_SERVICE_${PN} = "vsi-web-demo.service"

S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}/opt/vsi_web_demo
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${datadir}/applications
    cp -r ${S}/* ${D}/opt/vsi_web_demo/
    cp -r ${S}/.crossbar ${D}/opt/vsi_web_demo/
    install -m 0644 ${WORKDIR}/vsi-web-demo.service \
                    ${D}${systemd_system_unitdir}/vsi-web-demo.service
    install -m 0644 ${WORKDIR}/vsi-demo.desktop \
                    ${D}${datadir}/applications/
}

FILES_${PN} = " \
    /opt/vsi_web_demo/* \
    /opt/vsi_web_demo/.crossbar/* \
    ${etcdir}/systemd/system/vsi-web-demo.service \
    ${datadir}/applications/vsi-demo.desktop \
"

