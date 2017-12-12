#!/bin/bash -e

# (C) 2017 Gunnar Andersson
# LICENSE: MPLv2

# User warning!

# A casual user will need to read the script to understand what the first
# parameter shall be to make it actually run:
#

# Fail script unless you give the magic value:
if [[ "$1" != CI_FLAG ]]; then

  cat <<EOT

READ THIS: This script was not designed for personal use.

If you still use it, you hereby agree to any applicable End-User License
Agreement (EULA) of the corresponding BSPs that are used.  You must read
and understand the obligations by studying the script.

It is still a useful script but a few things could be destructive, e.g.
"git reset --hard" if you use this in your development environment instead of
a throw-away environment like CI.

Please make sure you commit/backup local changes and read the script to
understand what it does!

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

fail=false

# This is some trickery, but quite useful.
# Return the value of a variable whose name is stored in another variable
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
    flag=
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

# Layer version overrides:
# Find and evaluate environment variables of type

# LAYER_<layername>_FORK
# LAYER_<layername>_COMMIT
# LAYER_<layername>_BRANCH
# LAYER_<layername>_TAG

# *NOTE*  Variables cannot have dashes in name therefore, underscores must be
# used!  For example: LAYER_meta_browser_COMMIT for meta-browser layer

# If an override variable is defined, build with this version of the layer
# instead.  If multiple conflicting values, then the most specific override
# (commit) wins

layer_override() {
  local name=$1 suffix var_name value d

  # Variables cannot have dashes in name therefore, underscores must be
  # used.  For example: LAYER_meta_browser_COMMIT for meta-browser layer
  clean_name=$(echo "$name" | sed 's/-/_/g')

  # First FORK...
  var_name="LAYER_${clean_name}_FORK"
  value=$(deref "$var_name")
  if [ -n "$value" ] ; then
    echo "***** NOTE: OVERRIDING LAYER $name USING \$${var_name} = $value *****"
    d="$PWD"
    cd $name || echo "Layer name wrong?  Can't cd to dir : $name"
    echo "Fetching from $value"
    git remote add temp_fork "$value"
    git fetch temp_fork

    # Master is assumed, but it is likely overridden by a COMMIT/TAG/BRANCH
    # value below. but this could fail.
    echo "NOTE: Assuming master branch for now (might be overridden later)"
    echo "NOTE: This will fail if no master branch exists in $value"
    echo "+ git reset temp_fork/master --hard"
    git reset temp_fork/master --hard
    cd "$d"
  fi

  # ...then the others
  for suffix in COMMIT TAG BRANCH ; do
    var_name="LAYER_${clean_name}_${suffix}"
    value=$(deref "$var_name")
    if [ -n "$value" ] ; then
      echo "***** NOTE: OVERRIDING LAYER $name USING \$${var_name} = $value *****"
      d="$PWD"
      cd $name || echo "Layer name wrong?  Can't cd to dir : $name"
      git fetch --all

      # If forked, make sure to use the right remote, otherwise origin
      # For branches, prefer the explicit: remote/branchname
      if git remote | grep -q temp_fork ; then
        git checkout "temp_fork/$value" 2>/dev/null || git checkout $value
      else
        git checkout "origin/$value" 2>/dev/null || git checkout $value
      fi

      cd "$d"
      break  # First one wins, priority order: COMMIT >= TAG >= BRANCH
    fi
  done
}

# Because of the order they are called we can't use output from the
# layer_override function above, so we have some repeated code here.
print_layer_overrides() {
  local names="$1" name clean_name suffix var_name value d

  for name in $names ; do
    clean_name=$(echo "$name" | sed 's/-/_/g')
    for suffix in COMMIT TAG BRANCH ; do
      var_name="LAYER_${clean_name}_${suffix}"
      value=$(deref "$var_name")
      if [ -n "$value" ] ; then
        echo "$var_name = $value"
      fi
    done
  done
}

# FIXME: Asking git to list submodules would be better
LAYERS="
meta-browser
meta-erlang
meta-genivi-dev
meta-intel
meta-iot-web
meta-ivi
meta-linaro
meta-oic
meta-openembedded
meta-qcom
meta-qt5
meta-raspberrypi
meta-renesas
meta-rvi
poky
renesas-rcar-gen3
"

# Clean up function called at end of script, or if interrupted
cleanup() {
  # Restore git config user - if this was not defined locally before then it is
  # unset (which might mean a global setting is used)
  if [ -z "$olduser" ] ; then
    git config --unset user.name
  else
    git config user.name "$olduser"
  fi
  if [ -z "$oldemail" ] ; then
    git config --unset user.email
  else
    git config user.email "$oldemail"
  fi
}

# ---- Main program ----

trap cleanup SIGINT SIGTERM

D=$(dirname "$0")
cd "$D"
BASEDIR=$(readlink -f "$PWD/..")

# The user must define this
ensure_var_is_defined TARGET

stop_if_failure

# The values can be overridden by defining environment variables
# If no value given, use this default:
define_with_default BUILD_SDK false
define_with_default COPY_LICENSES false
define_with_default LAYER_ARCHIVE false
define_with_default CREATE_RELEASE_DIR false
define_with_default MIRROR "https://docs.projects.genivi.org/releases/yocto_mirror"
define_with_default PREMIRROR ""  # By default none (but we have the shared DL_DIR)
define_with_default RM_WORK false
define_with_default REUSE_STANDARD_DL_DIR true
define_with_default REUSE_STANDARD_SSTATE_DIR true
define_with_default SGX_DRIVERS $AGENT_STANDARD_SGX_LOCATION
define_with_default SGX_GEN_3_DRIVERS $AGENT_STANDARD_SGX_GEN3_LOCATION
define_with_default SOURCE_ARCHIVE false
define_with_default STANDARD_RELEASE_BUILD false

# The following only apply to temporary (local) dirs.  If any of
# REUSE_STANDARD_{DL,SSTATE}_DIR is defined then those directories will be
# used no matter what. Those reusable DL/SSTATE dirs are never cleared by
# this script.
define_with_default KEEP_DOWNLOADS false
define_with_default KEEP_SSTATE false

define_with_default KEEP_TMP false

stop_if_failure

git_gdp="https://github.com/GENIVI/genivi-dev-platform"
branch="master"

# Special case: Use porter script to copy graphics drivers for koelsch
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

# OVERRIDING VARIABLES
if [[ "$REUSE_STANDARD_DL_DIR" == "true" ]]; then
  DL_DIR="$AGENT_STANDARD_DL_DIR"
fi

if [[ "$REUSE_STANDARD_SSTATE_DIR" == "true" ]]; then
  SSTATE_DIR="$AGENT_STANDARD_SSTATE_DIR"
fi

if [[ "$STANDARD_RELEASE_BUILD" == "true" ]]; then
  SOURCE_ARCHIVE=true  # NOTE: overriding env settings
  COPY_LICENSES=true
  LAYER_ARCHIVE=true
  CREATE_RELEASE_DIR=true
fi

echo Configuration:
echo
echo "TARGET = $TARGET"
echo "FORK = $FORK"
echo "BRANCH = $BRANCH"
echo "TAG = $TAG"
echo "COMMIT = $COMMIT"
echo "RELEASE = $RELEASE (currently unused)"
print_layer_overrides "$LAYERS"
echo "BUILD_SDK = $BUILD_SDK"
echo "COPY_LICENSES" = "$COPY_LICENSES"
echo "CREATE_RELEASE_DIR" = "$CREATE_RELEASE_DIR"
echo "DL_DIR = $DL_DIR"
echo "MIRROR" = "$MIRROR"
echo "PREMIRROR" = "$PREMIRROR"
echo "REUSE_STANDARD_DL_DIR = $REUSE_STANDARD_DL_DIR"
echo "REUSE_STANDARD_SSTATE_DIR = $REUSE_STANDARD_SSTATE_DIR"
echo "RM_WORK = $RM_WORK"
echo "SGX_DRIVERS = $SGX_DRIVERS"
echo "SGX_GEN_3_DRIVERS = $SGX_GEN_3_DRIVERS"
echo "SOURCE_ARCHIVE" = "$SOURCE_ARCHIVE"
echo "SSTATE_DIR = $DL_DIR"
echo "STANDARD_RELEASE_BUILD" = "$STANDARD_RELEASE_BUILD"

# build steps
cd "$BASEDIR/gdp-src-build"

# If DL/SSTATE are to be reused it is normally by the use of
# REUSE_STANDARD_DL_DIR and REUSE_STANDARD_SSTATE_DIR.  Local dirs left in the
# build directory are therefore normally wiped, unless these environment
# variables say otherwise.

if [[ "$KEEP_DOWNLOADS" != "true" ]] ; then
  rm -rf downloads
fi

if [[ "$KEEP_SSTATE" != "true" ]]; then
   rm -rf cache sstate-cache
fi

if [[ "$KEEP_TMP" != "true" ]]; then
   rm -rf tmp
fi

cd "$BASEDIR"

# Need to set an identity for some git patching done by recipes
set +e  # The following two commands can fail if value is unset
olduser="$(git config user.name)"
oldemail="$(git config user.email)"
set -e
git config user.name "CI build -- ignore"
git config user.email no_email

# Normally the material (source code) is defined in the pipeline itself in the
# CIAT system but there are multiple ways to override it provided here.

if [[ -n "$FORK" ]]; then
  echo "***** NOTE: OVERRIDING REPOSITORY WITH \$FORK = $FORK *****"
  git remote set-url origin "$FORK"
  git fetch
  # V *danger* V.  One reason why you should not use this script if it's not in CI
  git reset origin/master --hard
  # After this, submodule sync/update will be done by init.sh
  # this may need some further testing!
  git checkout $BRANCH  # <- note this should be ok even if $BRANCH is an empty value
fi

if [[ -n "$BRANCH" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$BRANCH = $BRANCH *****"
  git checkout $BRANCH
fi

if [[ -n "$TAG" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$TAG = $TAG *****"
  git checkout $TAG
fi

if [[ -n "$COMMIT" ]]; then
  echo "***** NOTE: OVERRIDING CHOSEN COMMIT USING \$COMMIT = $COMMIT *****"
  git checkout $COMMIT
fi

# Deal with special setup, copy binary drivers etc.
if [[ "$TARGET" == "r-car-m3-starter-kit" || "$TARGET" == "r-car-h3-starter-kit" ]];  then
  cd meta-renesas
  meta-rcar-gen3/docs/sample/copyscript/copy_evaproprietary_softwares.sh /var/go/sgx_bin_gen3/
  cd -
fi

if [[ "$GFX_MACHINE" == "porter" || "$GFX_MACHINE" == silk ]]; then
  echo "Copying binary graphics drivers for $GFX_MACHINE"
  cd meta-renesas/meta-rcar-gen2
  ./copy_gfx_software_$GFX_MACHINE.sh /var/go/sgx_bin
  ./copy_mm_software_lcb.sh /var/go/sgx_bin/
  cd -
fi

# INIT
cd "$BASEDIR"
echo "Running init.sh"

# By using this script you accept the EULA (see top of script)
if [[ "$TARGET" == "dragonboard-410c" ]]; then
  source ./init.sh $TARGET accept-eula -f
else
  source ./init.sh $TARGET -f
fi

# Do version override on sublayers (if any such overrides defined)
cd ..
for l in $LAYERS ; do
  layer_override $l
done
cd gdp-src-build

# Remind us exactly what submodules hashes are used (this is already stated by
# go.cd when fetching materials, but materials can be overriden by FORK /
# BRANCH / TAG / COMMIT environment variables
echo "Submodules (after any updates or overrides):"
git submodule status

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

if [[ "$SOURCE_ARCHIVE" == "true" ]]; then
  append_local_conf 'INHERIT += "archiver"'
  append_local_conf 'ARCHIVER_MODE[src] = "original"'
fi

if [[ "$COPY_LICENSES" == "true" ]]; then
  append_local_conf 'COPY_LIC_DIRS = "1"'
  append_local_conf 'COPY_LIC_MANIFEST = "1"'
fi

# The own-mirrors bbclass is generally more convenient for PREMIRRORS, but
# this format makes it similar to the $MIRROR setup below, which needs to be
# explicit anyhow.
if [[ -n "$PREMIRROR" ]]; then
  append_local_conf "
PREMIRRORS_prepend = \"\\
     git://.*/.* $PREMIRROR \\n \\
     ftp://.*/.* $PREMIRROR \\n \\
     http://.*/.* $PREMIRROR \\n \\
     https://.*/.* $PREMIRROR \\n \\
     \"
"
fi

# This is the "post" mirror (i.e. checked last).
if [[ -n "$MIRROR" ]]; then
  append_local_conf "
MIRRORS_append = \"\\
     git://.*/.* $MIRROR \\n \\
     ftp://.*/.* $MIRROR \\n \\
     http://.*/.* $MIRROR \\n \\
     https://.*/.* $MIRROR \\n \\
     \"
"
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

cd "$BASEDIR"
rm -f logs.tar logs.tar.gz
find gdp-src-build/tmp/work \( -name "*.log" -o -name "log.*" -o -name "run.*" \) -print0 | xargs -0 tar uf logs.tar || true
gzip logs.tar || true

# Soften up the failure requirements here.  Maybe sometimes
# some things can't be staged, which is probably ok.
set +e
rm -rf staging
shopt -s nullglob
stage_artifact mv gdp-src-build/tmp/deploy/licenses
stage_artifact mv gdp-src-build/tmp/deploy/licenses/genivi-dev-platform*/license.manifest
stage_artifact mv gdp-src-build/tmp/deploy/sdk*
stage_artifact cp gdp-src-build/tmp/deploy/images/*
stage_artifact mv gdp-src-build/tmp/deploy/sources
stage_artifact cp gdp-src-build/conf/*.conf
stage_artifact mv logs.tar.gz
stage_artifact cp gdp-src-build/buildhistory/images/*/glibc/genivi-dev-platform/files-in-image.txt
stage_artifact mv gdp-src-build/buildhistory
stage_artifact mv gdp-src-build/tmp/buildstats

if [[ "$LAYER_ARCHIVE" == "true" ]]; then
  tar cfj staging/meta-layers-snapshot.tar.bz2 meta-* poky renesas* gdp-src-build/conf
fi

set -e

# Environment contains alot of variables from Go.CD that specify the built
# version/hash, and other metadata.  Let's store them for future reference.
# and other relevant info
cd "$BASEDIR"
build_info_file=staging/build_info.txt

env >$build_info_file
git submodule status >>$build_info_file
echo 'For conf , see files *.conf, and any diff below' >>$build_info_file
git diff gdp-src-build/conf/templates/*.inc >>$build_info_file

mkdir -p staging/images
mv staging/*/{*201*ext*,*201*rootfs*,*sdimg*,*qemuboot.conf*,modules*.tgz,*hddimg*,bzImage*201*,*201*.iso,*201*.wic,*.efi,*.dtd} staging/images/ 2>/dev/null || true
cd staging && rm -rf "$TARGET"
cd "$BASEDIR"

if [[ "$CREATE_RELEASE_DIR" == "true" ]]; then
  set +e
  mkdir -p release
  echo "Moving images to release/"
  mv staging/images release/ 2>/dev/null || true
  echo "Moving staging/sources to release/"
  cp -a staging/sources release/ 2>/dev/null
  echo "Moving staging/licenses into release/"
  mv staging/licenses release/
  echo "Copying various metadata to release"
  cp staging/files-in-image.txt release/ 2>/dev/null
  cp staging/build_info.txt release/ 2>/dev/null
  cp staging/license.manifest release/ 2>/dev/null
  set -e
fi

set +e
echo "Artifacts in staging/ and release/"
ls -al staging/ release/
echo
echo "...in release/images/ :"
ls -al release/images/
set -e

cleanup
