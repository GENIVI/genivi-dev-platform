# Base this image on generic IVI image
require recipes-yocto-ivi/images/ivi-image.inc

DESCRIPTION = "GENIVI Demo Platform image which includes currently \
three proof-of-concepts (PoC's), the GENIVI Browser PoC, the Fuel- \
Stop-Adviser (FSA) PoC and the GENIVI AudioManager (AM) PoC."

PV = "1.2+snapshot-${DATE}"

IMAGE_FEATURES_append = " \
    ssh-server-openssh    \
    "

IMAGE_INSTALL_append = " \
    packagegroup-abstract-component-p1 \
    packagegroup-placeholder-component-p1 \
    packagegroup-specific-component-p2 \
    packagegroup-abstract-component-p2 \
    packagegroup-specific-component-p1 \
    packagegroup-gdp-am-poc \
    packagegroup-gdp-browser \
    packagegroup-gdp-fsa \
    packagegroup-gdp-qt5 \
    packagegroup-gdp-gps \
    boost \
    "

IMAGE_INSTALL_append_koelsch = " \
    gles-kernel-module \
    libegl \
    libegl-dev \
    libgbm-dev \
    "
