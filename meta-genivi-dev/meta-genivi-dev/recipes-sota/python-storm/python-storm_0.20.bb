SUMMARY = "Storm is an object-relational mapper (ORM) for Python developed at Canonical"
HOMEPAGE = "https://pypi.python.org/pypi/storm"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

SRC_URI = "https://launchpad.net/storm/trunk/0.20/+download/storm-0.20.tar.gz"
SRC_URI[md5sum] = "8628503141f0f06c0749d607ac09b9c7"
SRC_URI[sha256sum] = "0fa70043bb1a1c178c2f760db35f5956244cecf50dab7fb22d78be7507726603"

S = "${WORKDIR}/storm-0.20"

inherit setuptools

RDEPENDS_${PN} += "python \
    python-setuptools \
    python-pkgutil \
    python-modules \
    "

do_compile_prepend() {
    BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} \
    ${STAGING_BINDIR_NATIVE}/python-native/python setup.py install || true
}

# need to export these variables for python-config to work
export PYTHONPATH
export BUILD_SYS
export HOST_SYS
export STAGING_INCDIR
export STAGING_LIBDIR

FILES_${PN} = " \
    ${libdir}/${PYTHON_DIR}/site-packages \
    ${libdir}/${PYTHON_DIR}/site-packages/*"

FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/.debug/*"
