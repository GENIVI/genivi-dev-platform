# Note that this script is supposed to be sourced

function setupGitSubmodules() {
    local choice=$1
    if [[ $2 == "accept-eula" ]] ; then
        local eula=true
    fi
    echo "You selected target $choice"

    local targets=(
        "qemux86-64"
        "koelsch"
        "porter"
        "raspberrypi2"
        "raspberrypi3"
        "minnowboard"
        "silk"
        "dragonboard-410c"
        "r-car-m3-starter-kit")
    local supported=(
        "qemux86-64"
        "porter"
        "minnowboard"
        "raspberrypi2"
        "raspberrypi3"
        "dragonboard-410c"
        "silk"
        "r-car-m3-starter-kit")
    local modules=()
    local machine=""

    for i in ${targets[@]}
    do
        if [[ $i == $choice ]]
        then
            machine=$choice
            echo "$machine is a valid target"
            break
        fi
    done

    if test -z "$machine" ; then
        echo "An invalid target name was given of $choice, available targets are:"
        printf '%s\n' "${targets[@]}"
        echo "Usage: source init.sh target [accept-eula] [-f]"
        return 1
    else
        if [[ ! ${supported[@]} =~ "$machine" ]] && [[ ${!#} != "-f" ]]; then
            echo "However currently, only the following targets are officially supported:"
            printf '%s\n' "${supported[@]}"
            echo "You can either "
            echo "re-run with a supported target (or override this check with -f)"
            echo "or get the gdp-11 branch:"
            echo "   git checkout origin/gdp-11 and then re-run"
            echo "   source init.sh $machine"
            return 1
        fi
    fi

    if [[ "$machine" == "dragonboard-410c" ]] && test -z "$eula" ; then
        echo "Building GDP for DragonBoard 410c requires Qualcomm EULA acceptance."
        echo "See http://git.yoctoproject.org/cgit/cgit.cgi/meta-qcom/tree/conf/EULA for details."
        echo "Please re-run init.sh with additional 'accept-eula' argument"
        return 1
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
    bsparr["minnowboard"]="meta-intel"
    bsparr["raspberrypi2"]="meta-raspberrypi"
    bsparr["raspberrypi3"]="meta-raspberrypi"
    bsparr["koelsch"]="meta-renesas"
    bsparr["porter"]="meta-renesas"
    bsparr["silk"]="meta-renesas"
    bsparr["dragonboard-410c"]="meta-qcom"
    bsparr["r-car-m3-starter-kit"]="renesas-rcar-gen3"

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

    return 0
}


if setupGitSubmodules $1 $2 $3 ; then
    source poky/oe-init-build-env gdp-src-build
    echo
    echo "Now run:  bitbake genivi-dev-platform"
fi
