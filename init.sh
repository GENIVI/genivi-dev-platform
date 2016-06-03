#!/bin/bash

# Parse target board from arg

choice=$1

echo "You selected target $choice"

declare -a targets=("qemux86-64" "porter" "raspberrypi2" "raspberrypi3" "minnowboard" "silk")

for i in ${targets[@]}; do
   if [[ ${i} == $choice ]] ; then
      machine=$choice
      echo "$machine is a valid target"
      break
   fi
done
if test -z "$machine" ; then
   echo "An invalid target name was given of $choice, available targets are:"
   printf '%s\n' "${targets[@]}"
   unset machine choice
   return
fi

echo "Generating bitbake configuration files for $machine"

cp gdp-src-build/conf/templates/${machine}.local.conf gdp-src-build/conf/local.conf
cp gdp-src-build/conf/templates/${machine}.bblayers.conf gdp-src-build/conf/bblayers.conf
echo "Local & bblayers conf set for $machine"

# init really makes sense only the first time
# and after that is redundant, only require target
# bsp submodule layer
declare -A bsparr

bsparr+=( ["minnowboard"]="meta-intel" ["raspberrypi2"]="meta-raspberrypi" ["raspberrypi3"]="meta-raspberrypi" ["porter"]="meta-renesas" ["silk"]="meta-renesas")

modules=($(git submodule | awk '{ print $2 }'))
for i in ${bsparr[@]}; do
   if [[ "$machine" == "qemux86-64" ]] || [[ $i != ${bsparr[${machine}]} ]] ; then
      modules=(${modules[@]//*$i*})
   fi
done

git submodule init "${modules[@]}"

# git submodule sync helpfully rewrites your remotes (typically "origin")
# inside the submodules :) I bet this helpfulness will drive some power
# users mad, but for most users this will reduce instead of add confusion
git submodule sync "${modules[@]}"

# update here could help ensure people get the right checked out version
# after they have switched branches. However learning how submodules work
# is better (because they are not that user friendly otherwise...))
git submodule update "${modules[@]}"

unset machine choice modules bsp bsparr

source poky/oe-init-build-env gdp-src-build

echo
echo "Now run:  bitbake genivi-dev-platform"
