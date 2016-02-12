meta-genivi-demo: the Yocto layer for the GENIVI Demo Platform
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
    [here](https://at.projects.genivi.org/jira/projects/GDP/issues).
Read or Edit the wiki
    [here](https://at.projects.genivi.org/wiki/display/GDP).
For information about the Yocto Project, see the
    [Yocto Project website](https://www.yoctoproject.org).  
For information about the Yocto GENIVI Baseline, see the
    [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).
IRC Channel
    #automotive - Freenode

Building the Genivi Demo Platform (GDP)
--------------------------------------
To build the GDP, GENIVI maintain a git sub-module repo with branches specific for
the supported build targets:
    [genivi-demo-platform.git](http://git.projects.genivi.org/?p=genivi-demo-platform.git;a=summary)

For example, to generate the build environment for the QEMUx86-64 target:

$ mkdir GDP
$ cd GDP
$ git clone --recursive http://git.projects.genivi.org/genivi-demo-platform.git -b qemux86-64
$ cd genivi-demo-platform
$ source init.sh
$ bitbake genivi-demo-platform

More specific information on build targets, including build steps and deployments instructions
for each supported target, check [here](https://at.projects.genivi.org/wiki/display/GDP)


Layer Dependency List
------------------
URI: git://git.yoctoproject.org/meta-ivi
> branch: 9.0  *baseline version can change*
> Poky Version: Fido 1.8

URI: git://github.com/meta-qt5/meta-qt5.git
> branch: fido

URI: git://git.openembedded.org/meta-openembedded
> branch: fido

URI: git://git.yoctoproject.org/poky
> branch: fido

## The Renesas R-Car Gen2 Koelsch & Porter boards depend in addition on: ##
URI: git://github.com/slawr/meta-renesas.git
> branch: genivi-7.0-bsp-1.8.0

## The Intel Minnowboard MAX depends in addition on: ##
URI: git://git.yoctoproject.org/meta-intel
> branch: fido

Supported Machines
------------------

We support the builds for these machines:

* QEMU (x86-64) - emulated machine: qemux86-64
* Renesas R-Car Gen2 (R-Car M2) - machine: koelsch
* Renesas R-Car Gen2 (R-Car M2) - machine: porter
* Renesas R-Car Gen2 (R-Car E2) - machine: silk
* Intel Minnowboard MAX (x86-64) - machine: minnowboard

Miscellaneous
-------------
For the Fuel Stop Advisor Proof of Concept (FSA PoC), a navigation map
must be downloaded. Once booted, issue the following command on the board:

# cd /usr/share/navit/maps/ && wget http://www.navit-project.org/switzerland.bin
