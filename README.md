meta-genivi-dev: the Yocto layer for the GENIVI Development Platform
====================================================================

This layer provides a GENIVI Development Platform (GDP) image build. The layer
supports cross-architecture application development using QEMU
emulation and an SDK.

Please see the  
[MAINTAINERS](https://github.com/genivi/meta-genivi-dev/blob/master/MAINTAINERS)
file for information on contacting the maintainers of this layer, as well as
instructions for submitting patches.

The GENIVI Development Platform project welcomes contributions. You can contribute
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

Building the GENIVI Development Platform (GDP)
----------------------------------------------
To build the GDP, GENIVI maintains a git sub-module repo, which supports multiple targets:
    [genivi-dev-platform.git](https://github.com/genivi/genivi-dev-platform/)

For example, to generate the build environment for the QEMUx86-64 target:

$ mkdir GDP  
$ cd GDP  
$ git clone http://github.com/genivi/genivi-dev-platform.git  
$ cd genivi-dev-platform  
$ source init.sh qemux86-64 (check below for currently supported targets)  
$ bitbake genivi-dev-platform  

If you are reading this as a developer you should be familiar with the instructions on the [GDP Master](https://at.projects.genivi.org/wiki/display/GDP/GDP+Master) page.

More specific information on build targets, including build steps and deployment instructions
for each supported target, check [here](https://at.projects.genivi.org/wiki/display/GDP/GDP+releases)

Layer Dependency List
---------------------
URI: git://git.yoctoproject.org/meta-ivi
* branch:   12.0 (N-1.0 tag)

URI: https://github.com/meta-qt5/meta-qt5.git
* branch:   krogoth

URI: git://git.openembedded.org/meta-openembedded
* layers:   meta-oe, meta-ruby, meta-filesystems, meta-python
* branch:   morty

URI: git://git.yoctoproject.org/poky
* branch:   morty

URI: git://git.yoctoproject.org/git/meta-oic.git
* branch: 1.0.1

URI: git://github.com/GENIVI/meta-rvi.git
* branch: master

URI: git://github.com/joaohf/meta-erlang.git
* branch: master

## The Raspberry Pi 2,3 boards depends in addition on: ##

URI: git://git.yoctoproject.org/meta-raspberrypi
* branch:   morty

## The Renesas R-Car Gen3 M3/H3 Starter Kit boards depend in addition on: ##
URI: https://github.com/slawr/renesas-rcar-gen3.git
* branch: genivi-11

URI: https://git.linaro.org/openembedded/meta-linaro.git
* branch: krogoth

## The Renesas R-Car Gen2 Silk & Porter boards depend in addition on: ##
URI: git://github.com/slawr/meta-renesas.git
* branch: genivi-11-bsp-1.10.0

## The Intel Minnowboard MAX depends in addition on: ##
URI: git://git.yoctoproject.org/meta-intel
* branch: morty

## The Qualcomm Dragonboard 410c depends in addition on: ##
URI: git://git.yoctoproject.org/meta-qcom
* branch: morty

Supported Machines
------------------
We aim to support the builds for these machines:

* QEMU (x86-64)                  - machine: qemux86-64
* Renesas R-Car Gen2 (R-Car M2)  - machine: koelsch
* Renesas R-Car Gen2 (R-Car M2)  - machine: porter
* Renesas R-Car Gen2 (R-Car E2)  - machine: silk
* Renesas R-Car Gen3 (R-Car M3)  - machine: r-car-m3-starter-kit
* Intel Minnowboard MAX (x86-64) - machine: minnowboard
* Raspberry Pi 2                 - machine: raspberrypi2
* Raspberry Pi 3                 - machine: raspberrypi3
* Qualcomm Dragonboard 410c      - machine: dragonboard-410c

Miscellaneous
-------------
For the QEMU machine, in order to have audio, the emulation should be done like:
(please adjust to your own paths)

```
$ QEMU_AUDIO_DRV=pa ../../poky/scripts/runqemu ivi-image-demo qemux86-64 audio
```

For the Fuel Stop Advisor Proof of Concept (FSA PoC), a navigation map
must be downloaded. Once booted, issue the following command on the board:

```
# cd /usr/share/navit/maps/ && wget http://www.navit-project.org/switzerland.bin
```
