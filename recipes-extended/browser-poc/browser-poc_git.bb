SUMMARY = "GENIVI browser PoC"
DESCRIPTION = "A D-Bus-based software component to test the \
usability and quality of a web browser that uses the D-Bus interface."

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=815ca599c9df247a0c7f619bab123dad"

SRCREV = "531bd41d00509840cf5dfc767995549c27211341"

DEPENDS = "qtbase qtwebkit"

SRC_URI = "git://git.projects.genivi.org/browser-poc.git \
           file://browser_poc_smaller_bookmarks_qml.patch \
           file://browser.service \
           file://demoui.service \
           file://COPYING \
          "

S = "${WORKDIR}/git"
inherit qmake5

do_install_append() {
    mkdir -p ${D}/opt/browser/bin/
    install ${B}/browser/browser ${D}/opt/browser/bin/
    cp -r ${S}/demoui/images ${D}/opt/demoui
    mkdir -p ${D}/opt/testapp/bin/
    install ${B}/testapp/testapp ${D}/opt/testapp/bin/
    cp -r ${S}/testapp/images ${D}/opt/testapp
    cp -r ${S}/testapp/qml ${D}/opt/testapp
    mkdir -p ${D}/etc/systemd/user
    cp ${WORKDIR}/browser.service ${WORKDIR}/demoui.service ${D}/etc/systemd/user
}

FILES_${PN} += "/opt/browser/bin/* \
                /opt/demoui/* \
                /opt/testapp/* \
               "

FILES_${PN}-dbg += "/opt/browser/bin/.debug/* \
                    /opt/demoui/bin/.debug/* \
                    /opt/testapp/bin/.debug/* \
                   "
