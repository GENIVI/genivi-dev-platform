#!/bin/sh

# Convenient script to create commit for update of submodules.
#
# NOTE: This script can only handle one submodule update. In the spirit of
# keeping commits atomic, we typically shouldn't update multiple submodules in
# the same commit anyway.
#
# Author: Zeeshan Ali <zeeshan.ali@pelagicore.com>

SHORTLOG="$*"
if test -z "${SHORTLOG}"; then
    echo "Usage: ${0} <commit summary>"
    exit 1
fi

CHANGED_MOD=$(git submodule status | grep '^+' | tr -d + | head -n 1)
if test -z "${CHANGED_MOD}"; then
    echo "No module updated."
    exit 2
fi

SUBMODULE=$(echo ${CHANGED_MOD} | cut -d' ' -f2)
NEWCOMMIT=$(echo ${CHANGED_MOD} | cut -d' ' -f1)
OLDCOMMIT=$(git diff ${SUBMODULE}|grep "^-Subproject"|cut -d' ' -f3)

MSG="${SUBMODULE}: ${SHORTLOG}

$(git -C ${SUBMODULE} log --no-merges --format="%h %s" ${OLDCOMMIT}..${NEWCOMMIT})
"

git commit -s -e -m "${MSG}" "${SUBMODULE}"
