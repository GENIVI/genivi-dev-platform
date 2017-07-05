# LAYER_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
LCONF_VERSION = "6"

BBPATH = "${TOPDIR}"
BBFILES ?= ""

BBLAYERS ?= " \
  ${TOPDIR}/../poky/meta \
  ${TOPDIR}/../poky/meta-yocto \
  ${TOPDIR}/../poky/meta-yocto-bsp \
  ${TOPDIR}/../meta-ivi/meta-ivi \
  ${TOPDIR}/../meta-ivi/meta-ivi-bsp \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-openembedded/meta-filesystems \
  ${TOPDIR}/../meta-openembedded/meta-ruby \
  ${TOPDIR}/../meta-openembedded/meta-gnome \
  ${TOPDIR}/../meta-openembedded/meta-python \
  ${TOPDIR}/../meta-gplv2 \
  ${TOPDIR}/../meta-qt5 \
  ${TOPDIR}/../meta-genivi-dev/meta-genivi-dev \
  ${TOPDIR}/../meta-genivi-dev/meta-ivi \
  ${TOPDIR}/../meta-genivi-dev/meta-qt5 \
  ${TOPDIR}/../meta-genivi-dev/meta-rvi \
  ${TOPDIR}/../meta-oic \
  ${TOPDIR}/../meta-iot-web \
  ${TOPDIR}/../meta-erlang \
  ${TOPDIR}/../meta-rvi \
  ${TOPDIR}/../meta-browser \
  "

BBLAYERS_NON_REMOVABLE ?= " \
  ${OEROOT}/meta \
  ${OEROOT}/meta-yocto \
  ${OEROOT}/../meta-ivi/meta-ivi \
  "

# Include local developer settings. This can be used to locally modify the
# setup of mirrors, caching and any other bitbake variables.
# The include statement has no effect if the file does not exist.
include ${TOPDIR}/developer.conf
