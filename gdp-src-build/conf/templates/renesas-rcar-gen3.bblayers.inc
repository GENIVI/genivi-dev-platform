# Renesas R-Car Gen 3 (H3/M3/E3) layer configuration.
# Place the general layer setup for Gen 3 boards in a single include
# to avoid duplicating this information in multiple board files.

BBLAYERS += " \
  ${TOPDIR}/../meta-renesas/meta-rcar-gen3 \
  ${TOPDIR}/../meta-linaro/meta-linaro-toolchain \
  ${TOPDIR}/../meta-genivi-dev/meta-linaro-gdp/meta-linaro-toolchain \
  ${TOPDIR}/../meta-linaro/meta-optee \
  ${TOPDIR}/../meta-ivi-renesas \
  "
