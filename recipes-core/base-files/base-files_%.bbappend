do_configure_append() {
       echo "\033[9;0]" >> ${WORKDIR}/issue

       echo "/dev/mmcblk1p1       /Data/mnt-c           auto       defaults 0 2" >> ${WORKDIR}/fstab
       echo "/dev/mmcblk1p1       /Data/mnt-wt          auto       defaults 0 2" >> ${WORKDIR}/fstab
}

dirs755 += " /Data/mnt-c /Data/mnt-wt"
