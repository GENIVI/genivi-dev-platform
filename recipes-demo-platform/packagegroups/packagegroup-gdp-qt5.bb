DESCRIPTION = "QT5 components"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

PR = "r0"

inherit packagegroup

PACKAGES = "\
    packagegroup-gdp-qt5 \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    qtbase \
    qtbase-dev \
    qtbase-fonts \
    qtbase-fonts-pfa \
    qtbase-fonts-pfb \
    qtbase-fonts-qpf \
    qtbase-fonts-ttf-dejavu \
    qtbase-fonts-ttf-vera \
    qtbase-plugins \
    qtbase-staticdev \
    qtbase-tools \
    qtdeclarative \
    qtdeclarative-plugins \
    qtdeclarative-qmlplugins \
    qtdeclarative-tools \
    qtquick1 \
    qtquick1-plugins \
    qtquick1-tools \
    qtwayland \
    qtwayland-examples \
    qtwayland-plugins \
    qtwayland-tools \
    qtwebkit \
    qtwebkit-qmlplugins \
    "
