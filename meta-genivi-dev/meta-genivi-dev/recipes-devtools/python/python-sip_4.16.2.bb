SUMMARY = "Runtime helper for sip-generated python wrapper libraries"
SECTION = "devel/python"
HOMEPAGE = "http://www.riverbankcomputing.co.uk/sip"
AUTHOR = "Phil Thompson"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://../LICENSE-GPL2;md5=e91355d8a6f8bd8f7c699d62863c7303"
DEPENDS = "python qtbase"
RDEPENDS_${PN} = "python-core"

# riverbankcomputing is upstream, but keeps only latest version, sf usually have few older
#SRC_URI = "http://www.riverbankcomputing.com/static/Downloads/sip4/sip-${PV}.tar.gz"
SRC_URI = "${SOURCEFORGE_MIRROR}/project/pyqt/sip/sip-${PV}/sip-${PV}.tar.gz"
SRC_URI[md5sum] = "1ca96f90a7496a3c9664fdaa76205719"
SRC_URI[sha256sum] = "a55a2324a46ab42e42ae57c52ef06583b17d25c987973fe2e7ff2e8a649294ce"

S = "${WORKDIR}/sip-${PV}/siplib"

inherit qmake5 python-dir


EXTRA_QMAKEVARS_POST += " TEMPLATE=lib \
                         CONFIG=console \
                         DESTDIR= \
                         VERSION=1.0.0 \
                         TARGET=sip \
                         DEFINES=SIP_QT_SUPPORT \
                         INCLUDEPATH+=. \
                         INCLUDEPATH+=${STAGING_INCDIR}/${PYTHON_DIR} \
                         INCLUDEPATH+=${STAGING_INCDIR}"


do_configure_prepend() {
    cat ${S}/siplib.sbf.in | sed s,target,TARGET, | sed s,sources,SOURCES, | sed s,headers,HEADERS, | sed s,@CFG_MODULE_BASENAME@,sip, > ${S}/siplib.pro
    cat ${S}/siplib.c.in | sed s,@CFG_MODULE_BASENAME@,sip, > ${S}/siplib.c
    cat ${S}/sip.h.in | sed -e s,@CFG_MODULE_NAME@,sip,g > ${S}/sip.h
}

do_install() {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/
    install -m 0755 libsip.so.1.0.0 ${D}${libdir}/${PYTHON_DIR}/site-packages/sip.so
    # sipconfig.py sipdistutils.py
    install -d ${D}${includedir}
    install -m 0644 ${S}/sip.h ${D}${includedir}/sip.h
}

FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages/sip.so"

FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/.debug/*"
