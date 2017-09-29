#!/bin/sh

# Part of genivi-dev-platform
# (C) Gunnar Andersson
# License: MPLv2

# Update poky, etc. to the versions required by the current meta-ivi baseline

fail() {
  echo "Something failed.  You might need to clean the layer somehow (see above).  Giving up."
  exit 1
}

update_version() {
  layer=$1
  cd meta-ivi
  v=$(scripts/get_layer_info_from_README.sh $layer revision)
  cd ../$layer
  echo "* Updating $layer"
  git fetch origin
  git checkout $v || fail
  cd ..
}

echo "Reminder: Did you update submodules (is meta-ivi is at the right commit?)"
echo "Hit return to continue, or CTRL-C now"
read x

# Align directory
original_dir="$PWD"
d=$(dirname "$0")
cd "$d" # This ought to be scripts/
cd ..   # This ought to be the root

update_version poky
update_version meta-openembedded
update_version meta-gplv2

echo
echo "Done.  Run git diff in project root to see the status"

cd "$original_dir"

