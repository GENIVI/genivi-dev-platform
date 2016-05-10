# This script should be sourced 

# Check that it was sourced
cmd=$(basename "$0")
[ "$cmd" = "init.sh" ] && { 
   echo "No, the script needs to be _sourced_ from your current shell." 
   echo "Use source $cmd  or  . $cmd" 
   exit 1
}

# User to select a target board

while test -z "$machine" ; do

    echo -e "Enter target; 'qemux86-64', 'porter', 'raspberrypi2', 'minnowboard': \c "

    read choice

    echo "You selected target $choice"

    declare -a targets=("qemux86-64" "porter" "raspberrypi2" "minnowboard")

    for i in ${targets[@]}; do
        if [[ ${i} == $choice ]] ; then
            machine=$choice
	    echo "$machine is a valid target"
            break
        fi
    done
    if test -z "$machine" ; then
        echo "An invalid target name was given of $choice, please enter valid target"
    fi
done

cp gdp-src-build/conf/templates/${machine}.local.conf gdp-src-build/conf/local.conf
cp gdp-src-build/conf/templates/${machine}.bblayers.conf gdp-src-build/conf/bblayers.conf
echo "Local & bblayers conf set for $machine"

# Unset variables
unset machine
unset choice

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
