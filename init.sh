# This script should be sourced 

# Check that it was sourced
cmd=$(basename "$0")
[ "$cmd" = "init.sh" ] && { 
   echo "No, the script needs to be _sourced_ from your current shell." 
   echo "Use source $cmd  or  . $cmd" 
   exit 1
}

# Set the machine
declare -a supported_machines=("raspberrypi2" "raspberrypi3")

machine="raspberrypi2"

for i in ${supported_machines[@]}; do
    if [[ "${i}" == "$1" ]]; then
        machine=$1
        break
    fi
done

sed -i "/MACHINE/d" gdp-src-build/conf/auto.conf
echo "MACHINE = \"${machine}\"" >> gdp-src-build/conf/auto.conf

# init really makes sense only the first time
# and after that is redundant
git submodule init

# git submodule sync helpfully rewrites your remotes (typically "origin")
# inside the submodules :) I bet this helpfulness will drive some power
# users mad, but for most users this will reduce instead of add confusion
git submodule sync

# update here could help ensure people get the right checked out version
# after they have switched branches. However learning how submodules work
# is better (because they are not that user friendly otherwise...))
git submodule update 

source poky/oe-init-build-env gdp-src-build

echo
echo "Now run:  bitbake genivi-dev-platform"
