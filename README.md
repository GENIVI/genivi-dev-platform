About GDP: GENIVI Development Platform
================================

GENIVI Development Platform is the integration and delivery project that brings together all components developed by GENIVI experts and provides them to developers and users in a consumable way. You can find all the relevant information about GDP in the wiki:
* GDP [landing page](https://projetcs.genivi.org/gdp)
* GDP [Download page](https://projetcs.genivi.org/gdp/download)
* GDP [Roadmap](https://projetcs.genivi.org/gdp/roadmap)
* GDP-ivi9 [feature page](https://projetcs.genivi.org/gdp/gdp9)
* GDP-ivi9 [ports to target boards](https://at.projects.genivi.org/wiki/display/GDP/GDP+target+boards%2C+virtualization+and+peripherals)
* [GDP In Detail](https://at.projects.genivi.org/wiki/pages/viewpage.action?pageId=11567879) with further information about GDP components.
* [GENIVI on GitHub](https://github.com/GENIVI)
* Information about how [GDP is managed](https://at.projects.genivi.org/wiki/display/GDP/GENIVI+Development+Platform+management)

Contribute to GDP
-----------------------

Please see the  [MAINTAINERS](https://github.com/genivi/meta-genivi-dev/blob/master/MAINTAINERS)
file for information on contacting the maintainers of this layer, as well as instructions for submitting patches.

The GENIVI Development Platform project welcomes contributions. You can contribute
code, submit patches, report bugs, answer questions on our mailing lists and
review and edit our documentation and much more.

Subscribe to the mailing list
    [here](https://lists.genivi.org/mailman/listinfo/genivi-projects).
IRC Channel
    #automotive in freenode.net
View or Report bugs
    [GENIVI uses JIRA as bug tracker and task management tool](https://at.projects.genivi.org/jira/projects/GDP/issues).
For information about the Yocto Project, see the
    [Yocto Project website](https://www.yoctoproject.org).  
For information about the Yocto GENIVI Baseline, see the
    [Yocto GENIVI Baseline website](http://projects.genivi.org/GENIVI_Baselines/meta-ivi).

gdp-submodules
----------------------

The project uses submodules to pull in dependencies

It is therefore recommended to :

```bash
git clone --recursive <thisrepo> -b <branch>
```
where branch is (for example) koelsch, porter, minnowboard or raspberrypi2
(you must check which branches exist)

The master branch is no longer maintained

If you forgot the recursive option, try:
```bash
git checkout <branch>
git submodule init
git submodule update
```

To build:
```
source init.sh
```
and then follow instructions

If you do anything more advanced you probably need to study
information on git submodules - they are a bit special to
work with...

One recommendation to improve usability a little: 
```bash
git config diff.submodule=log
```
