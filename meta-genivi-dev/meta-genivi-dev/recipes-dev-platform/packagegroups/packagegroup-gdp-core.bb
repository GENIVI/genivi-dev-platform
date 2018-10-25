# Copyright (C) 2014-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GENIVI Development Platform core packagegroup"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} += "\
    avahi-daemon \
    cannelloni \
    common-api-c++ \
    common-api-c++-dbus \
    common-api-c++-someip \
    connman \
    connman-client \
    dlt-daemon \
    dlt-daemon-systemd \
    openssh-sftp-server \
    udisks2 \
    udisks2-disk-manager \
"
