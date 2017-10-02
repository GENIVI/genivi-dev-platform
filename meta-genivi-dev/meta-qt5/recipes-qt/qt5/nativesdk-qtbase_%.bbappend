# There's a permissions problem that I have not found the true root cause
# but we need to work around it.
# qtbase somehow (not explicitly) installed the environment-setup.d dir
# with 775 permissions whereas others (cmake) installed it with 755.  This
# caused a conflict when the filesystem is populated from the cmake and
# qtbase RPMs. (The transaction check step will say there is a conflict
# between two packages installing environment-setup.d)
#
# WORKAROUND HACK: Create the directory here, in case it has not been, and
# make sure it has the right permissions.  Since this is a workaround that
# can be improved and is still under investigation, there will also be some
# detailed printed/logged here.
#
# Refer to the ticket for more details on the cmake and nativesdk-qtbase
# recipes.

do_install_append_class-nativesdk() {
   # Create dir - presumably this is enough and later steps will not touch
   # it if it is there already.
   mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d

   # Just for info, test and workaround for permission mismatch on directory
   # (It's possible this is not having any effect if dir was created for
   # the first time, and correctly, in the line above)
   ls -al ${D}${SDKPATHNATIVE} | fgrep environment-setup.d | fgrep "drwxr-xr-x"
   if [ $? -eq 0 ] ; then
      echo "nativesdk-qtbase.bbappend:do_install NOTE: environment-setup.d seems to have the right permissions:"
      ls -al ${D}${SDKPATHNATIVE} | fgrep environment-setup.d
   else
      echo "nativesdk-qtbase.bbappend:do_install NOTE: environment-setup.d did not have expected 755 permissions -- fixing!"
      ls -al ${D}${SDKPATHNATIVE} | fgrep environment-setup.d
      chmod 755 ${D}${SDKPATHNATIVE}/environment-setup.d
      ls -al ${D}${SDKPATHNATIVE} | fgrep environment-setup.d
   fi
   ls -al ${D}${SDKPATHNATIVE}/environment-setup.d | fgrep "drwxr-xr-x" || {
      echo "Permissions still not fixed?"
      ls -al ${D}${SDKPATHNATIVE}
   }
}

