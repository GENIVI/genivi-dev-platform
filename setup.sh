git submodule add http://git.projects.genivi.org/meta-genivi-demo.git
echo "NOTE: Consider git pull in meta-genivi-demo to update"

git submodule add git://git.yoctoproject.org/meta-ivi
cd meta-ivi
git checkout 0d780d0cfd38694ae5e6f0198adcb72684b01acc
cd ..

git submodule add https://github.com/meta-qt5/meta-qt5.git
cd meta-qt5
git checkout adeca0db212d61a933d7952ad44ea1064cfca747
cd ..

git submodule add git://git.openembedded.org/meta-openembedded
cd meta-openembedded
git checkout 6413cdb66acf43059f94d0076ec9b8ad6a475b35
cd ..

git submodule add git://git.yoctoproject.org/poky
cd poky
git checkout b630f2f53645fa8f5890b4732f251c354ad525a7
cd ..

# Koelsch:
git submodule add git://github.com/slawr/meta-renesas.git
cd meta-renesas
git checkout 0991fba7024ab57634390813b0aa92d5e330345b
cd ..

# For Porter:
#git submodule add git://github.com/slawr/meta-renesas.git
#cd meta-renesas
#git checkout 6e829fe6e422793bbb05ec563c8544154c0e9bd8
#cd ..

cd meta-genivi-demo/conf
export TEMPLATECONF=`pwd`
cd -

source poky/oe-init-build-env gdp-src-build


echo "Common targets are: genivi-demo-platform"

