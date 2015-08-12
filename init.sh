# This script should be sourced 

# init really makes sense only the first time
# and after that is redundant
git submodule init

# update here could help ensure people get the right checked out version
# after they have switched branches. However learning how submodules work
# is better (because they are not that user friendly otherwise...))
git submodule update 

cd meta-genivi-demo/conf
export TEMPLATECONF=`pwd`
cd -

source poky/oe-init-build-env gdp-src-build

echo "Now run:"
echo
echo "bitbake genivi-demo-platform"

