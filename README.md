meta-ivi-demo: the Yocto layer for the GENIVI Demo Platform
===========================================================

This layer provides a GENIVI Demo Platform (GDP)image build. The layer
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
    [here](http://bugs.genivi.org/describecomponents.cgi?product=GENIVI%20Demonstration%20Platform). 
Read or Edit the wiki
    [here](http://wiki.projects.genivi.org/index.php/meta-ivi).  
For information about the Yocto Project, see the
    [Yocto Project website](https://www.yoctoproject.org).  
For information about the Yocto GENIVI Baseline, see the
    [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).  
For information about the Yocto GENIVI Demo Platform, see the
    [Yocto GENIVI Demo Platform website](http://wiki.projects.genivi.org/index.php/GENIVI_Demo_Platform).

Layer Dependencies
------------------

URI: git://git.yoctoproject.org/meta-ivi
* branch:   9.0
* revision: bfd95c5021885ed61b58a33087a4ee8e3d2f32ad

URI: https://github.com/meta-qt5/meta-qt5.git
* branch:   fido
* revision: 90919b9d86988e7da01fa2c0a07246b5b5600a5d

URI: git://git.openembedded.org/meta-openembedded
* layers:   meta-oe, meta-ruby, meta-filesystems
* branch:   fido
* revision: 5b0305d9efa4b5692cd942586fb7aa92dba42d59

URI: git://git.yoctoproject.org/poky
* branch:   fido
* revision: eb4a134a60e3ac26a48379675ad6346a44010339

## The Raspberry Pi2 board depend in addition on: ##

URI: git://git.yoctoproject.org/meta-raspberrypi
* branch:   jethro 
* revision: f2cff839f52a6e6211337fc45c7c3eabf0fac113

## The Renesas R-Car Gen2 Koelsch & Porter boards depend in addition on: ##

URI: git://github.com/slawr/meta-renesas.git
* branch:   genivi-7.0-bsp-1.8.0
* revision: b42c0c82d628cc3e7af728df668cf4459a50621f

Supported Machines
------------------

We do support the builds for currently two machines:

* QEMU (x86-64) - emulated machine: qemux86-64
* Renesas R-Car Gen2 (R-Car M2) - machine: koelsch
* Renesas R-Car Gen2 (R-Car M2) - machine: porter
* Raspberry Pi2 - machine: raspberrypi2

Miscellaneous
-------------
When building for raspberrypi2, add the following to your local.conf:
> MACHINE = "raspberrypi2"
> GPU_MEM = "128"
> PREFERRED_VERSION_linux-raspberrypi = "4.1.%"
> PREFERRED_VERSION_weston = "1.9.0"
> PREFERRED_VERSION_wayland-ivi-extension = "1.9.0"
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
