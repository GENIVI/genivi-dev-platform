# GENIVI Development Platform
The GENIVI Development Platform integrates software components developed by GENIVI into a release that can be downloaded and run on popular hardware development boards.

## Download
Download an image for your platform below.

[Download page](https://at.projects.genivi.org/wiki/display/GDP/GDP+Download+page)

### More information on the GDP is below;
* GDP [project home page](https://projects.genivi.org/gdp)
* GDP [Master](https://projects.genivi.org/gdp/master): build the GDP from sources using Yocto
* GDP [releases](https://projects.genivi.org/gdp/releases): information about GDP releases, target boards and peripherals.
* GDP [management](https://projects.genivi.org/gdp/management): policies and other processes associated to the GDP project.


Contributing to GDP
----------------------------

The GENIVI Development Platform project welcomes contributions:
* Subscribe to the mailing list [here](https://lists.genivi.org/mailman/listinfo/genivi-projects).
* IRC Channel: #automotive in freenode.net
* View or Report bugs: [GENIVI uses JIRA as bug tracker and task management tool](https://at.projects.genivi.org/jira/projects/GDP/issues).

Send Pull Requests here on GitHub.  If that method for some reason is not
appropriate for you, just contact us on the mailing list and we will accept
patches in other ways.

Please see the  [CONTRIBUTING.md](https://github.com/genivi/genivi-dev-platform/blob/master/CONTRIBUTING.md) file for information on commit quality.

For information about the Yocto Project, check the [Yocto Project website](https://www.yoctoproject.org).  

For information about the Yocto GENIVI Baseline, see [Yocto GENIVI Baseline website](https://at.projects.genivi.org/wiki/display/PROJ/Yocto+GENIVI+Baseline)

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

The current supported targets are qemux86-64, raspberrypi2, raspberrypi3,
minnowboard, r-car-m3-starter-kit and r-car-h3-starter-kit.
Currently this requires the use of the bash shell.

The `init.sh` script handles the the `$target` specific bitbake configuration.
The `$target` templates can be found in gdp-src-build/templates, as well as common
configuration `.inc` files. `init.sh` also handles the relevant git submodule
initiation.

To build:

    bitbake genivi-dev-platform

Problems?  Just ask on the mailing list (defined above)
