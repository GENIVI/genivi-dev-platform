# This script should be sourced 

git submodule init

cd meta-genivi-demo/conf
export TEMPLATECONF=`pwd`
cd -

source poky/oe-init-build-env gdp-src-build

echo "Now run:"
echo
echo "bitbake genivi-demo-platform"

