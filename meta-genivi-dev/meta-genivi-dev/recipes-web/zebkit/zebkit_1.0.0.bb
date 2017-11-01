DESCRIPTION = "zebkit is a JavaScript library that follows easy OOP concept, provides HTML5 Canvas based Rich UI"
SUMMARY = "zebkit library provides impressive bunch of UI components"
HOMEPAGE = "https://github.com/barmalei/zebra"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "npm://registry.npmjs.org;name=zebkit;version=1.0.0"

NPM_SHRINKWRAP := "${THISDIR}/${PN}/npm-shrinkwrap.json"
NPM_LOCKDOWN := "${THISDIR}/${PN}/lockdown.json"

inherit npm

S = "${WORKDIR}/npmpkg"

LICENSE_${PN} = "Apache-2.0"
