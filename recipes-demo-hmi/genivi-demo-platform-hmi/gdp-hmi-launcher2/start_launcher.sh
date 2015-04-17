#! /bin/sh
device0="/dev/mmcblk1p1"

if [ -b "$device0" ]
then
    /bin/mount -t auto /dev/mmcblk1p1 /Data/mnt-c
    /bin/mount -t auto /dev/mmcblk1p1 /Data/mnt-wt
    /bin/mkdir /Data/mnt-c/Launcher
fi

/bin/systemctl start --user gdp-hmi-launcher2.service
