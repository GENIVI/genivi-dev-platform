
# I am not sure of why the unique commands in the unit file was introduced
# I just moved it to the new .bb file structure...

SRC_URI_append = "\
     file://weston.service \
"

do_install_append() {
     mkdir -p ${D}${systemd_unitdir}/system/
     cp ${WORKDIR}/weston.service ${D}${systemd_unitdir}/system/
     mkdir -p ${D}${systemd_unitdir}/system/multi-user.target.wants/
     ln -sf /lib/systemd/system/weston.service ${D}/${systemd_unitdir}/system/multi-user.target.wants/weston.service
}

