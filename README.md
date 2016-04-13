meta-genivi-demo: the Yocto layer for the GENIVI Demo Platform
==============================================================

This layer provides a GENIVI Demo Platform (GDP) image build. The layer
supports cross-architecture application development using QEMU
emulation and an SDK.

Please see the  
[MAINTAINERS](http://git.projects.genivi.org/?p=meta-genivi-demo.git;a=blob;f=MAINTAINERS)
file for information on contacting the maintainers of this layer, as well as
instructions for submitting patches.

The GENIVI Demo Platform project welcomes contributions. You can contribute
code, submit patches, report bugs, answer questions on our mailing lists and
review and edit our documentation and much more.

Subscribe to the mailing list
    [here](https://lists.genivi.org/mailman/listinfo/genivi-projects).
View or Report bugs
    [here](https://at.projects.genivi.org/jira/projects/GDP/issues).
Read or Edit the wiki
    [here](https://at.projects.genivi.org/wiki/display/GDP).
For information about the Yocto Project, see the
    [Yocto Project website](https://www.yoctoproject.org).  
For information about the Yocto GENIVI Baseline, see the
    [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).
IRC Channel
    #automotive - Freenode

Building the GENIVI Demo Platform (GDP)
---------------------------------------
To build the GDP, GENIVI maintains a git sub-module repo with branches specific for
the supported build targets:
    [genivi-demo-platform.git](http://git.projects.genivi.org/?p=genivi-demo-platform.git;a=summary)

For example, to generate the build environment for the QEMUx86-64 target:

$ mkdir GDP
$ cd GDP
$ git clone --recursive http://git.projects.genivi.org/genivi-demo-platform.git -b qemux86-64
$ cd genivi-demo-platform
$ source init.sh
$ bitbake genivi-demo-platform

More specific information on build targets, including build steps and deployment instructions
for each supported target, check [here](https://at.projects.genivi.org/wiki/display/GDP/GDP+target+boards%2C+virtualization+and+peripherals)


Layer Dependency List
---------------------
URI: git://git.yoctoproject.org/meta-ivi
* branch:   9.0
* revision: bfd95c5021885ed61b58a33087a4ee8e3d2f32ad

URI: https://github.com/meta-qt5/meta-qt5.git
* branch:   fido
* revision: 90919b9d86988e7da01fa2c0a07246b5b5600a5d

URI: git://git.openembedded.org/meta-openembedded
* layers:   meta-oe, meta-ruby, meta-filesystems
* branch:   fido
* revision: a7c1a2b0e6947740758136216e45ca6ca66321fc

URI: git://git.yoctoproject.org/poky
* branch:   fido
* revision: 900d7d6b59c36b2bdbd1c85febec99e80ab54f95

## The Raspberry Pi2 board depend in addition on: ##

URI: git://git.yoctoproject.org/meta-raspberrypi
* branch:   master
* revision: 519c387e3b97ecc21ac1d7b4fc9197298f289a71

## The Renesas R-Car Gen2 Koelsch & Porter boards depend in addition on: ##
URI: git://github.com/slawr/meta-renesas.git
* branch:   experimental-genivi-9-bsp-1.10.0-weston-1.9.0
* revision: 565dd7def3cd39724169939c68bb748f5888cafb

## The Intel Minnowboard MAX depends in addition on: ##
URI: git://git.yoctoproject.org/meta-intel
* branch: fido

Supported Machines
------------------
We support the builds for these machines:

* QEMU (x86-64)                  - machine: qemux86-64
* Renesas R-Car Gen2 (R-Car M2)  - machine: koelsch
* Renesas R-Car Gen2 (R-Car M2)  - machine: porter
* Renesas R-Car Gen2 (R-Car E2)  - machine: silk
* Intel Minnowboard MAX (x86-64) - machine: minnowboard
* Raspberry Pi 2                 - machine: raspberrypi2

Miscellaneous
-------------
When building for raspberrypi2, add the following to your local.conf:
> MACHINE = "raspberrypi2"
> GPU_MEM = "128"
> KERNEL_DEVICETREE = "bcm2709-rpi-2-b.dtb"
> PREFERRED_VERSION_linux-raspberrypi = "4.1.%"
> PREFERRED_VERSION_weston = "1.9.0"
> PREFERRED_VERSION_wayland-ivi-extension = "1.9.1"
> PREFERRED_VERSION_mesa = "11.%"
> PREFERRED_PROVIDER_virtual/egl = "mesa"
> PREFERRED_PROVIDER_virtual/libgles2 = "mesa"
> PREFERRED_PROVIDER_virtual/libgl = "mesa"
> PREFERRED_PROVIDER_virtual/mesa = "mesa"
> PREFERRED_PROVIDER_jpeg = "jpeg"

When building for koelsch, add the following to your local.conf:

> MACHINE = "koelsch"
> USE_GSTREAMER_1_00="1"
> LICENSE_FLAGS_WHITELIST = "commercial"
> MACHINE_FEATURES_append = " sgx"
> MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"
> PREFERRED_PROVIDER_virtual/libgles1 = ""
> PREFERRED_PROVIDER_virtual/libgles2 = "gles-user-module"
> PREFERRED_PROVIDER_virtual/egl = "libegl"
> PREFERRED_PROVIDER_virtual/libgl = ""
> PREFERRED_PROVIDER_virtual/mesa = ""
> PREFERRED_PROVIDER_libgbm = "libgbm"
> PREFERRED_PROVIDER_libgbm-dev = "libgbm"


When building for porter, add the following to your local.conf:

> MACHINE = "porter"
> LICENSE_FLAGS_WHITELIST = "commercial"
> SDKIMAGE_FEATURES_append = " staticdev-pkgs"
> MACHINE_FEATURES_append = " sgx"
> MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"
> PREFERRED_PROVIDER_virtual/libgles1 = ""
> PREFERRED_PROVIDER_virtual/libgles2 = "gles-user-module"
> PREFERRED_PROVIDER_virtual/egl = "libegl"
> PREFERRED_PROVIDER_virtual/libgl = ""
> PREFERRED_PROVIDER_virtual/mesa = ""
> PREFERRED_PROVIDER_libgbm = "libgbm"
> PREFERRED_PROVIDER_libgbm-dev = "libgbm"


For the QEMU machine, in order to have audio, the emulation should be done like:
(please adjust to your own paths)

$ QEMU_AUDIO_DRV=pa ../../poky/scripts/runqemu ivi-image-demo qemux86-64 audio


For the Fuel Stop Advisor Proof of Concept (FSA PoC), a navigation map
must be downloaded. Once booted, issue the following command on the board:

# cd /usr/share/navit/maps/ && wget http://www.navit-project.org/switzerland.bin

To build the RVI SOTA client into your GDP image, please check:
[here](https://at.projects.genivi.org/wiki/display/GDP/RVI+SOTA+Client)

Enable touch support on the GENIVI AMM Faytech V2 monitor for Porter & Raspberry Pi 2 add to local.conf:
USE_FAYTECH_MONITOR = "1"
