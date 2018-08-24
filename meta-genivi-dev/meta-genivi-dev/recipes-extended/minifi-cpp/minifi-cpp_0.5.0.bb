# NOTE!
#
# This recipe is only intended as a Proof of Concept. The reason being that
# the minifi-cpp carries a lot of its dependency in the "thirdparty" directory.
# Some of the thirdparty builds can be disabled and system libraries will be
# used instead but most of the components in "thirdparty" are hardcoded to be
# built and there is a lot of references to include directories in the various
# cmake files to "thirdparty". This would need to involve the upstream project
# to address which would allow to "system libraries" only.
#
# The proper clean-up would be to actually create a meta-minifi which provides
# the recipes for all the dependencies that are not available in upstream
# meta layers including recipe for minifi-cpp.
#
# Also note that not all compile time options are exposed in this recipe,
# again this is only a PoC to compile and run MiNiFi on a target. TBD what is
# actually to be done with this.


SUMMARY = "Apache NiFi - MiNiFi C++ is a complementary data collection \
approach that supplements the core tenets of NiFi in dataflow management, \
focusing on the collection of data at the source of its creation. The C++ \
implementation is an additional implementation to the one in Java with the \
aim of an even smaller resource footprint."

LICENSE = "MIT & GPLv2 & Apache-2.0 & EDL-1.0"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=b58267cabd65636363f906f126095ff7 \
    file://thirdparty/rapidjson-1.1.0/license.txt;md5=ba04aa8f65de1396a7e59d1d746c2125 \
    file://thirdparty/uuid/COPYING;md5=58dcd8452651fc8b07d1f65ce07ca8af \
    file://thirdparty/yaml-cpp-yaml-cpp-20171024/LICENSE;md5=6a8aaf0595c2efc1a9c2e0913e9c1a2c \
    file://thirdparty/date/LICENSE.txt;md5=b5d973344b3c7bbf7535f0e6e002d017 \
    file://thirdparty/spdlog-20170710/LICENSE;md5=6e5242b8f24d08c5e948675102937cc9 \
    file://thirdparty/cxxopts/LICENSE;md5=8de00431559a76a1b43f6fd44f8f6689 \
    file://thirdparty/civetweb-1.10/LICENSE.md;md5=da079d81be91ff1d1ca56dcd751f897f \
    file://thirdparty/pcap++/LICENSE;md5=911690f51af322440237a253d695d19f \
    file://thirdparty/pcap++/3rdParty/dirent-for-Visual-Studio/LICENSE;md5=b1177b216c5b9b15e89b9612a05ca461 \
    file://thirdparty/pcap++/3rdParty/LightPcapNg/LightPcapNg/LICENSE.txt;md5=3b79e2c09be0f47e2543c923ea2a0bd7 \
    file://thirdparty/libuvc-0.0.6/LICENSE.txt;md5=2f1963e0bb88c93463af750daf9ba0c2 \
"

SRC_URI = "\
    https://github.com/apache/nifi-minifi-cpp/archive/rel/minifi-cpp-${PV}.tar.gz \
    file://0001-MINIFICPP-562-Fixed-thirdparty-uuid-include-path.patch \
    file://0002-thirdparty-date-remove-usage-of-SYSTEM.patch \
    file://minifi.service \
"

SRC_URI[md5sum] = "2a63fd6692a55db2fedc99b986da32b4"
SRC_URI[sha256sum] = "b7fb566706504c660ce43cd466ede58b649e94fb51f57dab8be02eabf6ea0f73"

S = "${WORKDIR}/nifi-${BPN}-rel-${BPN}-${PV}"

DEPENDS = "\
    bison \
    bzip2 \
    curl \
    flex \
    icu \
    openssl \
    python3 \
    zlib \
"

RDEPENDS_${PN} = "\
    libcurl \
"

PACKAGECONFIG ?= "rocksdb"

PACKAGECONFIG[mqtt] = "-DENABLE_MQTT=ON,-DENABLE_MQTT=OFF,paho-mqtt-c"
PACKAGECONFIG[rocksdb] = "-DDISABLE_ROCKSDB=OFF,-DDISABLE_ROCKSDB=ON, rocksdb"
PACKAGECONFIG[libarchive] = "-DDISABLE_LIBARCHIVE=OFF,-DDISABLE_LIBARCHIVE=ON,libarchive"
PACKAGECONFIG[gps] = "-DENABLE_GPS=ON,-DENABLE_GPS=OFF,,libgps"

EXTRA_OECMAKE = "-DSKIP_TESTS=ON -DBUILD_ROCKSDB=OFF -DUSE_SYSTEM_UUID=OFF"

inherit cmake pkgconfig systemd

do_install_append() {
     rm ${D}/usr/NOTICE ${D}/usr/README.md ${D}/usr/LICENSE

     install -D -m 0644 ${WORKDIR}/minifi.service \
        ${D}${systemd_system_unitdir}/minifi.service
}

FILES_${PN} += "/usr/conf"
FILES_${PN}-dev += "/usr/lib/cmake"

SYSTEMD_SERVICE_${PN} = "minifi.service"
