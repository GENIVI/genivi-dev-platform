# Copyright (C) 2018 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

require genivi-dev-platform-core.bb

DESCRIPTION = "GENIVI Development Platform IVI image"

IMAGE_INSTALL_append = " \
    packagegroup-abstract-component-p1 \
    packagegroup-placeholder-component-p1 \
    packagegroup-specific-component-p2 \
    packagegroup-abstract-component-p2 \
    packagegroup-specific-component-p1 \
    packagegroup-gdp-am-demo \
    packagegroup-gdp-browser \
    packagegroup-gdp-hmi \
    packagegroup-gdp-qt5 \
    packagegroup-gdp-rvi \
    packagegroup-gdp-vsi \
    packagegroup-gdp-sdl \
"

# CES Demo
IMAGE_INSTALL_append = " franca-ara franca-ara-beta franca-ara-capi-server"
