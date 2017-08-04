#
# This bbappend will add the Simple Node State Machine to the
# node-state-manager build.
#
NSMC = "SimpleNodeStateMachine"

EXTRA_OECONF_append = " --with-nsmc=${NSMC}"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV_${NSMC} = "b4de5dee4d70a223eef37bcbb5f8536560b0e243"

#
# We add a secondary git repository embedded in the main repository and
# configure NSM to build that NSMC through the EXTRA_OECONF above. configure.ac
# also must be patched to actually include the new NSMC in the build. 
#
SRC_URI_append = " \
    git://github.com/GENIVI/simple-node-state-machine.git;protocol=https;destsuffix=git/${NSMC};name=${NSMC} \
    file://0001-Build-${NSMC}.patch \
"

