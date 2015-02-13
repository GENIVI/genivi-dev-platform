require genivi-demo-platform.bb

DESCRIPTION = "GENIVI Demo Platform image that includes everything within \
genivi-demo-platform plus meta-toolchain, development headers and libraries \
to form a standalone SDK."

inherit populate_sdk populate_sdk_qt5

QT5PKG = "qtcreator-debug"

IMAGE_FEATURES_append = "\
	debug-tweaks         \
	dev-pkgs             \
	eclipse-debug        \
	${QT5PKG}            \
	tools-debug          \
	tools-profile        \
	tools-sdk            \
	tools-testapps       \
	"

IMAGE_INSTALL_append  = "\
	kernel-dev           \
	"
