SUMMARY = "Python Qt5 Bindings"
HOMEPAGE = "http://riverbankcomputing.co.uk"
AUTHOR = "Phil Thomson @ riverbank.co.uk"
SECTION = "devel/python"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=027a10affabd63483e5a6ef03ed8590a \
"

DEPENDS = "sip-native python-sip python-dbus qtbase qtdeclarative qtsvg qtwebkit"
RDEPENDS_${PN} = "python-core"

PYQT_OE_VERSION = "Qt_5_3_1"
PR = "r1"

SRC_URI = "\
    http://sourceforge.net/projects/pyqt/files/PyQt5/PyQt-5.3.1/PyQt-gpl-5.3.1.tar.gz \
    file://qpycore.pro \
    file://qpygui.pro \
    file://dbus.pro \
    file://session_manager_feature_fix.patch \
"

SRC_URI[md5sum] = "c24b1a4040292fcfdbf128a9a5a38e66"
SRC_URI[sha256sum] = "187bc5b3e4e49e6b4ab1d5b20d33c5d0895524cf033dd21df193f6512abb19e8"
S = "${WORKDIR}/PyQt-gpl-${PV}"

inherit qmake5 sip pythonnative python-dir

B = "${S}"

PARALLEL_MAKE = ""

QMAKE_PROFILES = "pyqt.pro"
# NOTE: has to match with MIN(qt version we have in OE, last known Qt version by SIP/PyQt)
EXTRA_SIPTAGS = "-tWS_X11 -t${PYQT_OE_VERSION} -xVendorID -xPyQt_Accessibility -xPyQt_Desktop_OpenGL -xPyQt_SessionManager"
#EXTRA_OEMAKE = " MAKEFLAGS= "

SIP_MODULES = "QtCore QtGui QtNetwork QtQml QtQuick QtSvg QtWebKit QtWidgets QtOpenGL QtXmlPatterns"
MAKE_MODULES = "dbus qpy/QtCore qpy/QtGui ${SIP_MODULES}"

EXTRA_OECMAKE += "-DQT_NO_ACCESSIBILITY"

EXTRA_QMAKEVARS_POST += "\
    INCLUDEPATH+=${OE_QMAKE_INCDIR_QT}/Qt \
    INCLUDEPATH+=${STAGING_INCDIR}/${PYTHON_DIR} \
    INCLUDEPATH+=../../QtCore \
    INCLUDEPATH+=../qpy/QtCore \
    INCLUDEPATH+=../qpy/QtGui \
    INCLUDEPATH+=${STAGING_INCDIR}/dbus-1.0 \
    INCLUDEPATH+=${STAGING_LIBDIR}/dbus-1.0/include \
    INCLUDEPATH+=../qpy/QtQml \
    INCLUDEPATH+=../qpy/QtQuick \
    INCLUDEPATH+=${OE_QMAKE_INCDIR_QT}/QtCore \
    INCLUDEPATH+=${OE_QMAKE_INCDIR_QT}/QtGui \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtOpenGL \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtQml \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtQuick \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtWebKit \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtNetwork \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtSql \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtSvg \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtXmlPatterns \
    INCLUDEPATH+=${STAGING_INCDIR}/qt5/QtWidgets \
"

do_generate_prepend() {
    cp ${WORKDIR}/dbus.pro dbus/
}

do_configure_prepend() {
    cp ${WORKDIR}/qpycore.pro qpy/QtCore/
    cp ${WORKDIR}/qpygui.pro qpy/QtGui/
    if [ -e qpy/QtCore/qpycore_post_init.cpp.in ]
    then
        mv qpy/QtCore/qpycore_post_init.cpp.in qpy/QtCore/qpycore_post_init.cpp
    fi
    printf "TEMPLATE=subdirs\nSUBDIRS=${MAKE_MODULES}\n" >pyqt.pro
    printf "TEMPLATE=subdirs\nSUBDIRS=QtCore QtGui QtQml QtQuick\n" >qpy/qpy.pro
    ln -sf ./qpycore.pro qpy/QtCore/QtCore.pro
    ln -sf ./qpyqml.pro qpy/QtQml/QtQml.pro
    ln -sf ./qpyquick.pro qpy/QtQuick/QtQuick.pro
    ln -sf ./qpygui.pro qpy/QtGui/QtGui.pro
    echo "INCLUDEPATH+=${S}/QtCore" >>qpy/QtCore/QtCore.pro
    echo "INCLUDEPATH+=${S}/QtGui" >>qpy/QtGui/QtGui.pro
    echo "INCLUDEPATH+=${S}/QtQml" >>qpy/QtQml/QtQml.pro
    echo "INCLUDEPATH+=${S}/QtQuick" >>qpy/QtQuick/QtQuick.pro
    echo "LIBS+=-lQt5Widgets" >>QtWidgets/QtWidgets.pro
    echo "LIBS+=-lQt5Gui" >>QtGui/QtGui.pro
    echo "LIBS+=-L../qpy/QtCore/ -lqpycore" >>QtCore/QtCore.pro
    echo "LIBS+=-L../qpy/QtGui/ -lqpygui" >>QtGui/QtGui.pro
     echo "LIBS+=-ldbus-1 -lpython2.7" >>dbus/dbus.pro
    # hack for broken generated code (duplicated sipCpp declaration).
    patch -p1 < ${WORKDIR}/pyqt-generated.patch || echo "pyqt-generated.patch failed to apply, probably reexecuting do_configure, ignoring that"
}

do_install() {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5
    install -d ${D}${datadir}/sip/qt/
    install -d ${D}${bindir}
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/dbus/mainloop
    for module in ${SIP_MODULES}
    do
        install -m 0644 ${S}/sip/${module}/*.sip ${D}${datadir}/sip/qt/
        echo "from PyQt5.${module} import *\n" >> ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5/Qt.py
        install -m 0755 ${module}/lib${module}.so ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5/${module}.so
    done
    install -m 0755 ${S}/dbus/libpyqt5.so.1.0.0 ${D}${libdir}/${PYTHON_DIR}/site-packages/dbus/mainloop/pyqt5.so
    cp __init__.py ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5/
}

FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages ${datadir}/sip/qt/ ${bindir}"
FILES_${PN}-dbg += "${libdir}/${PYTHON_DIR}/site-packages/PyQt5/.debug/ ${libdir}/${PYTHON_DIR}/site-packages/dbus/mainloop/.debug/"
