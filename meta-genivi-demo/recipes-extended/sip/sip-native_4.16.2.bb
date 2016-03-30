SUMMARY = "SIP is a C++/Python Wrapper Generator"
AUTHOR = "Phil Thompson"
HOMEPAGE = "http://www.riverbankcomputing.co.uk/sip"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../LICENSE-GPL2;md5=e91355d8a6f8bd8f7c699d62863c7303"

# riverbankcomputing is upstream, but keeps only latest version, sf usually have few older
#SRC_URI = "http://www.riverbankcomputing.com/static/Downloads/sip4/sip-${PV}.tar.gz"
SRC_URI = "${SOURCEFORGE_MIRROR}/project/pyqt/sip/sip-${PV}/sip-${PV}.tar.gz"
SRC_URI[md5sum] = "1ca96f90a7496a3c9664fdaa76205719"
SRC_URI[sha256sum] = "a55a2324a46ab42e42ae57c52ef06583b17d25c987973fe2e7ff2e8a649294ce"
S = "${WORKDIR}/sip-${PV}/sipgen"

inherit qmake5 native python-dir

EXTRA_QMAKEVARS_POST += "DESTDIR=${S} CONFIG=console"

export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR

do_configure_prepend() {
    pwd
    cat ${S}/sipgen.sbf | sed s,target,TARGET, | sed s,sources,SOURCES, | sed s,headers,HEADERS, > ${S}/sipgen.pro
}
do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/sip ${D}${bindir}/sip
    # python-pyqt expects sip4
    ln -sf sip ${D}${bindir}/sip4
    cd ${WORKDIR}/sip-${PV} && python configure.py
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    install -m 0755 sip*.py ${D}${PYTHON_SITEPACKAGES_DIR}
}
