GENIVI Development Platform
========================

GENIVI Development Platform is the integration and delivery project that brings together all components developed by GENIVI experts and provides them to developers and users in a consumable way. You can find all the relevant information about GDP in the wiki:
* GDP [landing page](https://projects.genivi.org/gdp): GDP project home page.
* GDP [Download page](https://projects.genivi.org/gdp/download): download GDP binaries, metadata and artifacts.
* GDP [Master](https://projects.genivi.org/gdp/master): build GDP from scratch.
* GDP [releases](https://projects.genivi.org/gdp/releases): information about GDP releases, target boards and peripherals.
* GDP [management](https://projects.genivi.org/gdp/management): policies and other processes associated to the GDP project.

Contribute to GDP
----------------------------

Please see the  [MAINTAINERS](https://github.com/genivi/meta-genivi-dev/blob/master/MAINTAINERS) file for information on contacting them as well as instructions for submitting patches.

The GENIVI Development Platform project welcomes contributions:
* Subscribe to the mailing list [here](https://lists.genivi.org/mailman/listinfo/genivi-projects).
* IRC Channel: #automotive in freenode.net
* View or Report bugs: [GENIVI uses JIRA as bug tracker and task management tool](https://at.projects.genivi.org/jira/projects/GDP/issues).

For information about the Yocto Project, check the [Yocto Project website](https://www.yoctoproject.org).  

For information about the Yocto GENIVI Baseline, see [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).

genivi-dev-platform.git usage
------------------------------------

This project uses submodules to pull in layer dependencies.
It is advised to avoid using the --recursive option for the
initial clone. 'master' is the default branch. Previous release
'maintenance' branches are also available. Note certain tags
may require a different set of usage instructions, please refer
to the relative README.

    git clone <thisrepo> -b <branch>

To initiate the build environment:

    source init.sh $target

The current supported targets are qemux86-64, porter, raspberrypi2, minnowboard, silk.
Currently this requires the use of the bash shell

The `init.sh` script handles the the `$target` specific bitbake configuration.
The `$target` templates can be found in gdp-src-build/templates, as well as common
configuration `.inc` files. `init.sh` also handles the relevant git submodule
initiation.

To build:

    bitbake genivi-dev-platform

Please see the README in meta-genivi-dev for further information.
