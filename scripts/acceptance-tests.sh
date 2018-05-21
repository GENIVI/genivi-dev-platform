#!/bin/bash

set -x

# if called under Yocto/OE shell, BUILDDIR will already be set correctly
if [ -z "$BUILDDIR" ]; then
    # try to guess BUILDDIR
    BUILDDIR="`dirname $0`"/../gdp-src-build
fi

if [ -d ${BUILDDIR}/tmp ]; then
    BUILDTMP=${BUILDDIR}/tmp
else
    BUILDTMP=${BUILDDIR}/tmp-glibc
fi

QEMU_PIDFILE=/tmp/qemu.pid

./start-qemu-daemon

if [ ! -e ${QEMU_PIDFILE} ]; then
    echo "Failed to start QEMU"
    exit 1
fi

QEMU_BOOT_TIME_WAIT=${QEMU_BOOT_TIME_WAIT:-"10"}

# Give qemu some time to boot.
#
# The oe-test suite actually has a wait built in which is the "ping" test
# but it seems that we still pass ping but later on fail on ssh with
# "connection refused"
sleep ${QEMU_BOOT_TIME_WAIT}

QEMU_PID=$(cat ${QEMU_PIDFILE})

echo "started QEMU with PID ${QEMU_PID}"

TEST_DIR=${TEST_DIR:-"${BUILDTMP}/testexport/genivi-dev-platform"}

cd ${TEST_DIR}
./oe-test runtime
TEST_RESULT=$?
cd -

kill -TERM ${QEMU_PID}
rm -f ${QEMU_PIDFILE}

exit ${TEST_RESULT}
