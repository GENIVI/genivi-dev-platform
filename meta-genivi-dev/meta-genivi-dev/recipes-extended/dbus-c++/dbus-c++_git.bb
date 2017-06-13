require dbus-c++.inc

DEPENDS = "glib-2.0 dbus expat"

SRC_URI_append = " file://do_not_build_tools_examples_test.patch"