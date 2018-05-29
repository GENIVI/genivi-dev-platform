APPLICATION_ICON_SIZE ?= "64x64"
APPLICATION_ICON_TARGET_PATH ?= "${datadir}/app-info/icons/${DISTRO_VERSION}/${APPLICATION_ICON_SIZE}/com.genivi.gdp.${PN}.svg"
APPLICATION_DIR ?= "${exec_prefix}/apps"
APPLICATION_DIR_TARGET_PATH ?= "${APPLICATION_DIR}/${PN}/bin/"
APPLICATION_BIN_TARGET_PATH ?= "${APPLICATION_DIR_TARGET_PATH}/${PN}"
APPLICATION_UNIT ?= ""

inherit qmake5

S = "${WORKDIR}/git"

# Aligned with AppStream guidelines for install locations
do_install() {
    install -d ${D}${APPLICATION_DIR}/${PN}/bin
    install -m 0555 ${APPLICATION_BIN} ${D}${APPLICATION_BIN_TARGET_PATH}

    install -d ${D}${datadir}/app-info/icons/${DISTRO_VERSION}/${APPLICATION_ICON_SIZE}/
    install -m 0644 ${APPLICATION_ICON} ${D}${APPLICATION_ICON_TARGET_PATH}

    sed -i -e 's#[@]APPLICATION_ICON_TARGET_PATH[@]#${APPLICATION_ICON_TARGET_PATH}#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}
    sed -i -e 's#[@]APPLICATION_BIN_TARGET_PATH[@]#${APPLICATION_BIN_TARGET_PATH}#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}
    sed -i -e 's#[@]APPLICATION_UNIT[@]#${APPLICATION_UNIT}#' ${WORKDIR}/${APPLICATION_DESKTOP_FILE}

    install -d ${D}${datadir}/applications/
    install -m 0444 ${WORKDIR}/${APPLICATION_DESKTOP_FILE} \
                ${D}${datadir}/applications/
}

FILES_${PN} += "\
    ${datadir}/app-info/icons \
    ${APPLICATION_DIR} \
    "
