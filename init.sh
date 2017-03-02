#!/bin/bash
# Note that this script is supposed to be sourced
if [ ! -n "$BASH" ]
then
echo GDP and Yocto in general only works with bash as the interactive shell.
echo Please retry with bash
return
fi

# Parse target board & eula agreement from args

choice=$1

if [[ $2 == "accept-eula" ]] ; then
   eula=true
fi

echo "You selected target $choice"

declare -a targets=("intel-corei7-64" "qemux86-64" "porter" "raspberrypi2" "raspberrypi3" "minnowboard" "silk" "dragonboard-410c")
declare -a supported=("intel-corei7-64" "qemux86-64" "porter" "minnowboard" "raspberrypi2" "raspberrypi3" "dragonboard-410c" "silk")
declare -a variables=("choice" "eula" "machine" "modules" "bsp" "bsparr" "supported" "targets" "variables")

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
   unset "${variables[@]}"
   echo "Usage: source init.sh target [accept-eula] [-f]"
   return
else
   if [[ ! ${supported[@]} =~ "$machine" ]] && [[ ${!#} != "-f" ]]; then
      echo "However currently, only the following targets are officially supported:"
      printf '%s\n' "${supported[@]}"
      echo "You can either "
      echo "re-run with a supported target (or override this check with -f)"
      echo "or get the gdp-11 branch:"
      echo "   git checkout origin/gdp-11 and then re-run"
      echo "   source init.sh $machine"
      unset "${variables[@]}"
      return
   fi
fi

if [[ "$machine" == "dragonboard-410c" ]] && test -z "$eula" ; then
   echo "Building GDP for DragonBoard 410c requires Qualcomm EULA acceptance."
   echo "See http://git.yoctoproject.org/cgit/cgit.cgi/meta-qcom/tree/conf/EULA for details."
   echo "Please re-run init.sh with additional 'accept-eula' argument"
   unset "${variables[@]}"
   return
fi

echo "Generating bitbake configuration files for $machine"

cp gdp-src-build/conf/templates/${machine}.local.conf gdp-src-build/conf/local.conf
cp gdp-src-build/conf/templates/${machine}.bblayers.conf gdp-src-build/conf/bblayers.conf

# Accept EULA in local.conf
if [[ $machine == "dragonboard-410c" ]] ; then
   echo "ACCEPT_EULA_dragonboard-410c = "'"1"'"" >> gdp-src-build/conf/local.conf
fi

echo "Local & bblayers conf set for $machine"

# init really makes sense only the first time
# and after that is redundant, only require target
# bsp submodule layer
declare -A bsparr

bsparr+=( ["intel-corei7-64"]="meta-intel" ["minnowboard"]="meta-intel" ["raspberrypi2"]="meta-raspberrypi" ["raspberrypi3"]="meta-raspberrypi" ["porter"]="meta-renesas" ["silk"]="meta-renesas" ["dragonboard-410c"]="meta-qcom" )

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

unset "${variables[@]}"

source poky/oe-init-build-env gdp-src-build

echo
echo "Now run:  bitbake genivi-dev-platform"
