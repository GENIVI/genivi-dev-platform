#!/bin/bash -e

# (C) 2017 Gunnar Andersson
# LICENSE: MPLv2

# User warning!

# A casual user will need to read the script to understand what the first
# parameter shall be to make it run:
#
if [[ "$1" != CI_FLAG ]]; then

  cat <<EOT

READ THIS: This script was not designed for personal use.

If you still use it, you hereby agree to any applicable End-User License
Agreement (EULA) of the corresponding BSPs that are used.

It is still a useful script but a few things could be destructive if you use
this in your development environment instead of a "throw-away" environment like
CI.

Please make sure you read the script to understand what it does!

After that I hope you enjoy using it.

EOT

   exit 2
fi

# ---- General settings ----

# Fixed cache location on all agents that support this.
AGENT_STANDARD_DL_DIR="/var/cache/yocto/downloads"
AGENT_STANDARD_SSTATE_DIR="/var/cache/yocto/sstate"
AGENT_STANDARD_SGX_LOCATION="/var/go/sgx_bin"
AGENT_STANDARD_SGX_GEN3_LOCATION="/var/go/sgx_bin_gen3"

# ---- Helper functions ----

# This is some trickery, but quite useful
deref() { eval echo \$$1 ; }

ensure_var_is_defined() {
  var_name=$1
  current_value=$(deref $var_name)
  if [[ -z "$current_value" ]]; then
    echo "Please define environment var_name \$$var_name (found empty value)"
    fail=true  # We want all the errors reported, so don't halt yet.
  fi
}

define_with_default() {
  var_name=$1
  default="$2"
  current_value=$(deref $var_name)
  if [[ -z "$current_value" ]]; then
    eval $var_name="$default"
  fi
}

stop_if_failure() {
  if [[ "$fail" == "true" ]] ; then
    exit 1
  fi
}

stop_immediately() {
  echo "Fatal error occurred - stopping ci-build script"
  exit 2
}

append_local_conf() {
  LOCAL_CONF="$BASEDIR/gdp-src-build/conf/local.conf"
  if [[ -f "$LOCAL_CONF" ]]; then
    echo -n "Appending to local.conf: "
    cat <<EOT | tee -a "$LOCAL_CONF"
$1
EOT
  else
    echo "Fatal: Did not find local.conf where expected"
    stop_immediately
  fi
}

stage_artifact() {
  mkdir -p staging
  cmd=$1
  shift

  if [[ -z "$1" ]] ; then
      echo "Skipped an artifact pattern that matched nothing"
      return
  fi

  for f in $@ ; do  # <- could be empty glob list
    if [[ -e "$f" ]] ; then
      if [[ $cmd == cp && -d "$f" ]] ; then
        flag=-a
      elif [[ $cmd == cp ]] ; then
        flag=--preserve=all
      fi
      $cmd $flag "$f" staging/
    else
      echo "Skipping non-existing (or already moved) artifact: $f"
    fi
  done
}

# ---- Main program ----

D=$(dirname "$0")
cd "$D"
BASEDIR=$(readlink -f "$PWD/..")

# The user must define this
ensure_var_is_defined TARGET

stop_if_failure

# The values can be overridden by defining environment variables
# If no value given, use this default:
define_with_default BUILD_SDK false
define_with_default RM_WORK false
define_with_default REUSE_STANDARD_DL_DIR true
define_with_default REUSE_STANDARD_SSTATE_DIR true
define_with_default SGX_DRIVERS $AGENT_STANDARD_SGX_LOCATION
define_with_default SGX_GEN_3_DRIVERS $AGENT_STANDARD_SGX_GEN3_LOCATION

stop_if_failure

git_gdp="https://github.com/GENIVI/genivi-dev-platform"
branch="master"

# For copying graphics
if [[ "$TARGET" == "koelsch" ]]; then
  GFX_MACHINE=porter
else
  GFX_MACHINE=$TARGET
fi

# cd workingdir
MACHINE="$TARGET" # For most boards - exceptions handled below

if [[ "$TARGET" == "r-car-m3-starter-kit" ]]; then
  MACHINE="m3ulcb"
fi

ensure_var_is_defined MACHINE
export MACHINE

if [[ "$REUSE_STANDARD_DL_DIR" == "true" ]]; then
  DL_DIR="$AGENT_STANDARD_DL_DIR"
fi

if [[ "$REUSE_STANDARD_SSTATE_DIR" == "true" ]]; then
  SSTATE_DIR="$AGENT_STANDARD_SSTATE_DIR"
fi

echo Configuration:
echo
echo "TARGET = $TARGET"
echo "BRANCH = $BRANCH"
echo "COMMIT = $COMMIT"
echo "RELEASE = $RELEASE"
echo "BUILD_SDK = $BUILD_SDK"
echo "SGX_DRIVERS = $SGX_DRIVERS"
echo "SGX_GEN_3_DRIVERS = $SGX_GEN_3_DRIVERS"
echo "REUSE_STANDARD_DL_DIR = $REUSE_STANDARD_DL_DIR"
echo "REUSE_STANDARD_SSTATE_DIR = $REUSE_STANDARD_SSTATE_DIR"
echo "DL_DIR = $DL_DIR"
echo "SSTATE_DIR = $DL_DIR"
echo "RM_WORK = $RM_WORK"

# build steps
cd "$BASEDIR/gdp-src-build"

# If DL/SSTATE are to be reused it is only through. the use of
# REUSE_STANDARD_DL_DIR and REUSE_STANDARD_SSTATE_DIR -  I.e. if any are left
# in the build directory we assume they shall be wiped out and not reused.

rm -rf tmp downloads cache sstate-cache

cd "$BASEDIR"

# Need to set an identity for some git patching done by recipes
git config user.name "CI build -- ignore"
git config user.email no_email@genivigo.com

# Normally the material (source code) is defined in the pipeline itself in the
# CIAT system but there are multiple ways to override it provided here.

if [[ -n "$FORK" ]]; then
  echo "***** NOTE: OVERRIDING REPOSITORY WITH \$FORK DEFINITION *****"
  git remote set-url origin "$FORK"
  git fetch
  # V *danger* V.  One reason why you should not use this script if it's not in CI
  git reset origin/master --hard
  # After this, submodule sync/update will be done by init.sh
  # this may need some further testing!
  git checkout $BRANCH  # <- note this should be ok even if $BRANCH is an empty value
fi

if [[ -n "$BRANCH" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$BRANCH DEFINITION *****"
  git checkout $BRANCH
fi

if [[ -n "$TAG" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$TAG DEFINITION *****"
  git checkout $TAG
fi

if [[ -n "$COMMIT" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$COMMIT DEFINITION *****"
  git checkout $COMMIT
fi

# Remind us exactly what submodules hashes are used (this is already stated by
# go.cd when fetching materials, but materials can be overriden by FORK /
# BRANCH / TAG / COMMIT
echo "Submodules:"
git submodule

# Deal with special setup, copy binary drivers etc.
set -x
if [[ "$TARGET" == "r-car-m3-starter-kit" ]];  then
  cd renesas-rcar-gen3
  meta-rcar-gen3/docs/sample/copyscript/copy_evaproprietary_softwares.sh /var/go/sgx_bin_gen3/
  cd -
fi

if [[ "$GFX_MACHINE" == "porter" || "$GFX_MACHINE" == silk ]]; then
  cd meta-renesas/meta-rcar-gen2
  ./copy_gfx_software_$GFX_MACHINE.sh /var/go/sgx_bin
  ./copy_mm_software_lcb.sh /var/go/sgx_bin/
  cd -
fi
set +x

# INIT
cd "$BASEDIR"
echo "Running init.sh"
if [[ "$TARGET" == "dragonboard-410c" ]]; then
  source ./init.sh $TARGET accept-eula -f
else
  source ./init.sh $TARGET -f
fi

# LOCAL CONF MODIFICATIONS
if [[ "$RM_WORK" == "true" ]]; then
  append_local_conf 'INHERIT += "rm_work"'
fi

if [[ -n "$DL_DIR" ]]; then
  append_local_conf "DL_DIR = \"$DL_DIR\""
fi

if [[ -n "$SSTATE_DIR" ]]; then
  append_local_conf "SSTATE_DIR = \"$SSTATE_DIR\""
fi

if [[ -n "$BB_NUMBER_THREADS" ]]; then
  append_local_conf "BB_NUMBER_THREADS = \"$BB_NUMBER_THREADS\""
fi

if [[ -n "$PARALLEL_MAKE" ]]; then
  echo $PARALLEL_MAKE | egrep -q '^-j' || PARALLEL_MAKE="-j$PARALLEL_MAKE"
  append_local_conf "PARALLEL_MAKE = \"$PARALLEL_MAKE\""
fi

if [[ "$BUILD_SDK" != "true" ]]; then
  bitbake genivi-dev-platform
fi

if [[ "$BUILD_SDK" == "true" ]]; then
  bitbake genivi-dev-platform-sdk
fi

if [[ "$BUILD_SDK" == "true" ]]; then
  bitbake meta-ide-support
fi

rm -f logs.tar logs.tar.gz
find gdp-src-build/tmp/work \( -name "*.log" -o -name "log.*" -o -name "run.*" \) -print0 | xargs -0 tar uf logs.tar || true
gzip logs.tar || true

cd "$BASEDIR"
rm -rf staging
shopt -s nullglob
stage_artifact mv gdp-src-build/tmp/deploy/licenses
stage_artifact mv gdp-src-build/tmp/deploy/licenses/genivi-dev-platform*/license.manifest
stage_artifact mv gdp-src-build/tmp/deploy/sdk*
stage_artifact cp gdp-src-build/tmp/deploy/images/*/*
stage_artifact cp gdp-src-build/tmp/deploy/images/*
stage_artifact cp gdp-src-build/conf/*.conf
stage_artifact cp logs.tar.gz

mkdir -p staging/images
mv staging/{*201*ext*,*201*rootfs*,*sdimg*,*hddimg*,bzImage*201*} staging/images/ 2>/dev/null || true

echo Artifacts in staging/
ls -R staging/

