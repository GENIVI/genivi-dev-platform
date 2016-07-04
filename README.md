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
for each supported target, check [here](https://at.projects.genivi.org/wiki/display/GDP/GDP+target+boards%2C+virtualization+and+peripherals)

Layer Dependency List
---------------------
URI: git://git.yoctoproject.org/meta-ivi
* branch:   master
* revision: 9d72380c1d50d0c3469b85c3a43fe612b5ee1dd9

URI: https://github.com/meta-qt5/meta-qt5.git
* branch:   jethro
* revision: ea37a0bc987aa9484937ad68f762b4657c198617

URI: git://git.openembedded.org/meta-openembedded
* layers:   meta-oe, meta-ruby, meta-filesystems
* branch:   jethro
* revision: ad6133a2e95f4b83b6b3ea413598e2cd5fb3fd90

URI: git://git.yoctoproject.org/poky
* branch:   jethro
* revision: fc45deac89ef63ca1c44e763c38ced7dfd72cbe1

URI: git://github.com/jmesmon/meta-rust.git
* branch: master
* revision: f13ac9d48ae928b761d7be204fa8f877d41e7099

URI: git://git.yoctoproject.org/git/meta-oic.git
* branch: 1.0.1
* revision: 69146eaf8bc05c74c377e731b7e16d82854a4659

URI: git://github.com/GENIVI/meta-rvi.git
* branch: master
* revision: de9d548fe35e2cee8688faaae910b4f6f7fea17e

URI: git://github.com/joaohf/meta-erlang.git
* branch: master
* revision: 4d7eacc8e6593934ed5b0c8abc3d3e9dc339d849

## The Raspberry Pi 2,3 boards depends in addition on: ##

URI: git://git.yoctoproject.org/meta-raspberrypi
* branch:   master
* revision: 9912d38e97671704822d1aa05312a0439cb650d3

## The Renesas R-Car Gen2 Koelsch & Porter boards depend in addition on: ##
URI: git://github.com/slawr/meta-renesas.git
* branch:   stevel/genivi-10
* revision: f14dc3725cab24387f9f3acd8e5f6a500b42a73a

## The Intel Minnowboard MAX depends in addition on: ##
URI: git://git.yoctoproject.org/meta-intel
* branch: jethro
* revision: 2397181e99d3155c7a00e1756cec92b568d9a9eb

## The Qualcomm Dragonboard 410c depends in addition on: ##
URI: git://git.yoctoproject.org/meta-qcom
* branch: jethro
* revision: 505f490caf66deeb8ae4e8038a2546a8e0c7aca4

Supported Machines
------------------
We aim to support the builds for these machines:

* QEMU (x86-64)                  - machine: qemux86-64
* Renesas R-Car Gen2 (R-Car M2)  - machine: koelsch
* Renesas R-Car Gen2 (R-Car M2)  - machine: porter
* Renesas R-Car Gen2 (R-Car E2)  - machine: silk
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

Enable touch support on the GENIVI AMM Faytech V2 monitor add to local.inc:

```
USE_FAYTECH_MONITOR = "1"
```
