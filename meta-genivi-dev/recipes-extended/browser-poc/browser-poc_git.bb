SUMMARY = "GENIVI browser PoC"
DESCRIPTION = "A D-Bus-based software component to test the \
usability and quality of a web browser that uses the D-Bus interface."

LICENSE = "MPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=815ca599c9df247a0c7f619bab123dad"

SRCREV = "231265ba9cc7581f8fb36d9e7f862f211d43564a"

DEPENDS = "qtbase qtwebkit"

SRC_URI = "git://git.projects.genivi.org/browser-poc.git;protocol=http \
           file://browser_poc_smaller_bookmarks_qml.patch \
           file://browser.service \
           file://demoui.service \
           file://COPYING \
           file://layer_manager_surface_id.patch \
           file://0001-browser-missing-method-for-connect.patch \
           file://0002-browser-fix-error-reported-on-bad-cache-policy.patch \
           file://0003-demoui-the-UI-is-stretched.patch \
           file://0004-demoui-use-QML-WebView-instead-of-browser-service.patch \
          "

SRC_URI_append_qemux86-64 = "\
           file://0001-Demoui-Make-Graphic-working-for-Qemu-machine.patch \
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
