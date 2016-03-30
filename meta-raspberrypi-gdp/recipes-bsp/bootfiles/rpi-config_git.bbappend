DISABLE_OVERSCAN = "1"

do_deploy_append() {
    echo "avoid_warnings=2" >>${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "mask_gpu_interrupt0=0x400" >>${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
