# Standard GDP configuration defaults, inherited by all targets

DISTRO ?= "poky-ivi-systemd"
USE_GSTREAMER_1_00 ?= "1"
PACKAGE_CLASSES ?= "package_ipk"
EXTRA_IMAGE_FEATURES ?= "debug-tweaks"
USER_CLASSES ?= "buildstats image-mklibs image-prelink"
PATCHRESOLVE ?= "noop"
LICENSE_FLAGS_WHITELIST  += "commercial"

#INCOMPATIBLE_LICENSE ?= "GPLv3"

PREFERRED_VERSION_erlang-native = "18.2.3"
PREFERRED_VERSION_erlang = "18.2.3"

# Disk Space Monitoring during the build
#
# Monitor the disk space during the build. If there is less that 1GB of space or less
# than 100K inodes in any key build location (TMPDIR, DL_DIR, SSTATE_DIR), gracefully
# shutdown the build. If there is less that 100MB or 1K inodes, perform a hard abort
# of the build. The reason for this is that running completely out of space can corrupt
# files and damages the build in ways which may not be easily recoverable.
BB_DISKMON_DIRS = "\
    STOPTASKS,${TMPDIR},1G,100K \
    STOPTASKS,${DL_DIR},1G,100K \
    STOPTASKS,${SSTATE_DIR},1G,100K \
    ABORT,${TMPDIR},100M,1K \
    ABORT,${DL_DIR},100M,1K \
    ABORT,${SSTATE_DIR},100M,1K" 

# CONF_VERSION is increased each time build/conf/ changes incompatibly and is used to
# track the version of this file when it was generated. This can safely be ignored if
# this doesn't mean anything to you.
CONF_VERSION = "1"

# Support OE QA test suite export with bitbake -c testexport <image name>
INHERIT += "testexport"
TEST_EXPORT_ONLY = "1"
TEST_SUITES = "ping ssh scp date dmesg connman systemd gdp"
