SUMMARY = "Epoxy is a library for handling OpenGL function pointer management for you."
DESCRIPTION = "It hides the complexity of dlopen(), dlsym(), glXGetProcAddress(), \
eglGetProcAddress(), etc. from the app developer, with very little knowledge needed \
on their part. They get to read GL specs and write code using undecorated function names \
like glCompileShader()."
HOMEPAGE = "https://github.com/anholt/libepoxy"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=58ef4c80d401e07bd9ee8b6b58cf464b"

DEPENDS = "util-macros virtual/egl"

SRC_URI = "git://github.com/anholt/libepoxy.git;protocol=http"

SRCREV = "b2ae054b3aa0d6796b6936c7a89b8cce7cefe7ba"
S = "${WORKDIR}/git"

inherit autotools-brokensep

do_configure() {
    export NOCONFIGURE=yes
    cd ${S} && ./autogen.sh
    oe_runconf
}
