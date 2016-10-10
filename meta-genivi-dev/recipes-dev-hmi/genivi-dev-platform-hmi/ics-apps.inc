# Copyright (C) 2015-2016 GENIVI Alliance

do_install_append() {
     install -d ${D}/opt/${APP}/share/icons 
     install -m 0444 ${WORKDIR}/git/${APP}.svg \
             ${D}/opt/${APP}/share/icons/${APP}.svg 
     install -d ${D}/opt/${APP}/bin
     install -m 0555 ${EXE} \
                ${D}/opt/${APP}/bin/${EXE}
     install -d ${D}/usr/share/applications
     install -m 0444 ${WORKDIR}/git/${APP}.app \
                ${D}/usr/share/applications/${APP}.app
     install -d ${D}/usr/lib/systemd/user/
     install -m 0444 ${WORKDIR}/git/${APP}.service \
                ${D}/usr/lib/systemd/user/${APP}.service
}

FILES_${PN} += "\
    /opt/${APP} \
    /usr/bin/${EXE} \
    ${libdir}/systemd/user/* \
    "
