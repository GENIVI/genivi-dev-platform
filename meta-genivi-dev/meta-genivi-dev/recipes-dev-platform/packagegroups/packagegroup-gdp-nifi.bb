# Copyright (C) 2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GENIVI Development Platform Apache NiFi packagegroup"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = "\
    minifi-cpp \
"
