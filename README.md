# GENIVI Development Platform
========================

GENIVI Development Platform is the integration and delivery project that brings together all components developed by GENIVI experts and delivers them to developers and users in a consumable way. You can find all the relevant information about GDP through these links:
* GDP [landing page](https://projects.genivi.org/gdp)
* GDP [Download page](https://projects.genivi.org/gdp/download)
* GDP-ivi9 [feature page](https://projects.genivi.org/gdp/gdp9)
* GDP-ivi9 [ports to target boards](https://at.projects.genivi.org/wiki/display/GDP/GDP+target+boards%2C+virtualization+and+peripherals)
* [GDP In Detail](https://at.projects.genivi.org/wiki/pages/viewpage.action?pageId=11567879) with further information about GDP components.
* Information about how [GDP is managed](https://at.projects.genivi.org/wiki/display/GDP/GENIVI+Development+Platform+management)
* GDP [Roadmap](https://projects.genivi.org/gdp/roadmap)
* [GENIVI on GitHub](https://www.github.com/GENIVI)

## Contribute to GDP
----------------------------

Please see the  [MAINTAINERS](https://github.com/genivi/meta-genivi-dev/blob/master/MAINTAINERS) file for information on contacting them as well as instructions for submitting patches.

The GENIVI Development Platform project welcomes contributions:
* Subscribe to the mailing list [here](https://lists.genivi.org/mailman/listinfo/genivi-projects).
* IRC Channel: #automotive in freenode.net
* View or Report bugs: [GENIVI uses JIRA as bug tracker and task management tool](https://at.projects.genivi.org/jira/projects/GDP/issues).

For information about the Yocto Project, check the [Yocto Project website](https://www.yoctoproject.org).  

For information about the Yocto GENIVI Baseline, see [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).

## genivi-dev-platform.git usage
------------------------------------

This project uses submodules to pull in layer dependencies.
It is advised to avoid using the --recursive option for the
initial clone. 'master' is the default branch. Previous release
'maintenance' branches are also available. Note certain tags
may require a different set of usage instructions, please refer
to the relative README.
```
git clone <thisrepo> -b <branch>
```
To initiate the build environment:
```
source init.sh $target
```
The current supported targets are qemux86-64, porter, raspberrypi2, minnowboard, silk.
Currently this requires the use of the bash shell

The init.sh script handles the the $target specific bitbake configuration.
The $target templates can be found in gdp-src-build/templates, as well as common
configuration .inc files. init.sh also handles the relevant git submodule
initiation.

To build:
```
bitbake genivi-dev-platform
```

Please see the README in meta-genivi-dev for further information.
