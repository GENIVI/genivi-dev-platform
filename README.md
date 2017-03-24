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
* branch:   11.0
* revision: 28b25ef27eccc0a800a1cbd3d3ea2c8f8494ca1e

URI: https://github.com/meta-qt5/meta-qt5.git
* branch:   krogoth
* revision: d715f2c1d340fa38f8a9860acc73de5e14a38b75

URI: git://git.openembedded.org/meta-openembedded
* layers:   meta-oe, meta-ruby, meta-filesystems
* branch:   krogoth
* revision: 247b1267bbe95719cd4877d2d3cfbaf2a2f4865a

URI: git://git.yoctoproject.org/poky
* branch:   krogoth
* revision: 12eb72ee3b02f826a156ff4e396c770f2b93571e

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
* revision: 4c02c7ce07121c2f5367204445f93199d828bb10

## The Renesas R-Car Gen3 M3/H3 Starter Kit boards depend in addition on: ##
URI: https://github.com/slawr/renesas-rcar-gen3.git
* branch: genivi-11
* revision: 4758a558bb3badd7108b04b8c43a8a3fbe61b958

URI: https://git.linaro.org/openembedded/meta-linaro.git
* branch: krogoth
* revision: 2f51d38048599d9878f149d6d15539fb97603f8f

## The Renesas R-Car Gen2 Silk & Porter boards depend in addition on: ##
URI: git://github.com/slawr/meta-renesas.git
* revision: b22c42eb736bef0253667094a457d74b7e630fa3

## The Intel Minnowboard MAX depends in addition on: ##
URI: git://git.yoctoproject.org/meta-intel
* branch: krogoth
* revision: b8c199201ffe026485a14e1fcfc398e2b3551512

## The Qualcomm Dragonboard 410c depends in addition on: ##
URI: git://git.yoctoproject.org/meta-qcom
* branch: krogoth
* revision: 19ff1853a764ee1014f4e7a295e030b6ddc10612

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
