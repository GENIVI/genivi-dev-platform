require navit.inc

S = "${WORKDIR}/git"

SRC_URI += "git://github.com/navit-gps/navit.git;branch=trunk;protocol=http \
            file://search_list_get_unique.diff \ 
            file://0001-add-wayland-ilm-support-navit.patch \
            file://0002-add-precision-opengl-ilm-support-navit.patch \
            file://fsa_issue_padding.diff \
            file://avoid-crash-on-guidance-when-delete-and-recreate-route.diff \
"

SRCREV = "f5abdd317e10d56226300d001469595658a605e4"

