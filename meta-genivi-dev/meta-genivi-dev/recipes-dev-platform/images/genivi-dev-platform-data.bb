# Copyright (C) 2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

require genivi-dev-platform-core.bb

DESCRIPTION = "GENIVI Development Platform Big Data image"

IMAGE_INSTALL_append = " \
    packagegroup-gdp-rvi \
    packagegroup-gdp-nifi \
"
