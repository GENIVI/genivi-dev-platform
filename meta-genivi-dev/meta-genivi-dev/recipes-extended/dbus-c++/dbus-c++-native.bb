require dbus-c++.inc
inherit native

DEPENDS = "glib-2.0-native dbus-native expat-native"

SRC_URI_append = " file://0001-Fix-build-error-in-test.patch"
