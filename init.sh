# Note that this script is supposed to be sourced,
# and only works with bash or zsh, not a vanilla bourne shell

if [ -z "$BASH_VERSION" -a -z "$ZSH_VERSION" ] ; then
    echo This sript only works with bash or zsh as the interactive shell.
    echo Please retry with bash or zsh.
    return 1
fi

function setupGitSubmodules() {
    local choice=$1
    if [[ $2 == "accept-eula" ]] ; then
        local eula=true
    fi
    echo "You selected target $choice"

    local targets=(
        "dragonboard-410c"
        "koelsch"
        "minnowboard"
        "porter"
        "qemux86-64"
        "r-car-m3-starter-kit"
        "raspberrypi2"
        "raspberrypi3"
        "silk")
    local supported=(
        "dragonboard-410c"
        "minnowboard"
        "qemux86-64"
        "r-car-m3-starter-kit"
        "raspberrypi2"
        "raspberrypi3")
    local modules=()
    local target=""

    for i in ${targets[@]}
    do
        if [[ $i == $choice ]]
        then
            target=$choice
            echo "$target is a valid target"
            break
        fi
    done

    if test -z "$target" ; then
        echo "An invalid or empty target name was given: $choice  Available targets are:"
        printf '%s\n' "${targets[@]}"
        echo -e "\nUsage: source init.sh <target> [accept-eula] [-f]"
        return 1
    else
        if [[ ! ${supported[@]} =~ "$target" ]] && [[ ${!#} != "-f" ]]; then
            echo "However currently, only the following targets are officially supported:"
            printf '%s\n' "${supported[@]}"
            echo "You can either "
            echo "re-run with a supported target (or override this check with -f)"
            echo "or get the gdp-11 branch:"
            echo "   git checkout origin/gdp-11 and then re-run"
            echo "   source init.sh $target"
            return 1
        fi
    fi

    if [[ "$target" == "dragonboard-410c" ]] && test -z "$eula" ; then
        echo "Building GDP for DragonBoard 410c requires Qualcomm EULA acceptance."
        echo "See http://git.yoctoproject.org/cgit/cgit.cgi/meta-qcom/tree/conf/EULA for details."
        echo "Please re-run init.sh with additional 'accept-eula' argument"
        return 1
    fi

    echo "Generating bitbake configuration files for $target"

    cp gdp-src-build/conf/templates/${target}.local.conf gdp-src-build/conf/local.conf
    cp gdp-src-build/conf/templates/${target}.bblayers.conf gdp-src-build/conf/bblayers.conf

    # Accept EULA in local.conf
    if [[ $target == "dragonboard-410c" ]] ; then
        echo "ACCEPT_EULA_dragonboard-410c = "'"1"'"" >> gdp-src-build/conf/local.conf
    fi

    echo "Local & bblayers conf set for $target"

    # Define hardware-dependent layers.
    # Multiple layers can be specified for a target if space-separated.
    declare -A bsparr
    bsparr["qemux86-64"]=""
    bsparr["minnowboard"]="meta-intel"
    bsparr["raspberrypi2"]="meta-raspberrypi"
    bsparr["raspberrypi3"]="meta-raspberrypi"
    bsparr["koelsch"]="meta-renesas"
    bsparr["porter"]="meta-renesas"
    bsparr["silk"]="meta-renesas"
    bsparr["dragonboard-410c"]="meta-qcom"
    bsparr["r-car-m3-starter-kit"]="meta-linaro renesas-rcar-gen3"

    # This looks somewhat complex but the intention is to clone only needed
    # submodules.  The module list is calculated as : all the submodules we
    # have (as reported by git) *minus* BSP-related layers that are NOT
    # relevant for the chosen $target.  In other words - loop through all BSP
    # layers and delete them from array unless they are the layer (or layers)
    # defined for this $target.
    modules=($(git submodule | awk '{ print $2 }'))

    for i in ${bsparr[@]}; do
        local found=false

        # Multiple BSP layers per target are supported so loop over them just in case
        for bsp in ${bsparr[${target}]} ; do
            if [[ $i == $bsp ]] ; then
                found=true
            fi
        done

        if ! $found ; then
            # This BSP is not for the $target, so delete it from the list
            modules=(${modules[@]//*$i*})
        fi
    done

    git submodule init "${modules[@]}"

    git submodule sync "${modules[@]}"

    git submodule update "${modules[@]}"

    return 0
}


# Without a UTF-8 locale, the following complaint will be made by bitbake:
# "Please use a locale setting which supports utf-8.
#  Python can't change the filesystem locale after loading so we need a utf-8
#  when python starts or things won't work."
#
# Most interactive terminals might have a reasonable locale set, but just in
# case it is not, why not just fix it?
function setLocale() {
  env | egrep ^LC_ | egrep -q 'utf8|utf-8'
  if [ $? -ne 0 ] ; then
    echo "WARNING: Your locale did not look like UTF-8 enabled so it was now set to: LC_ALL=en_US.utf-8"
    export LC_ALL=en_US.utf-8
  fi
}


if setupGitSubmodules $1 $2 $3 ; then
    source poky/oe-init-build-env gdp-src-build
    setLocale
    echo
    echo "Now run:  bitbake genivi-dev-platform"
else
    return 1
fi
