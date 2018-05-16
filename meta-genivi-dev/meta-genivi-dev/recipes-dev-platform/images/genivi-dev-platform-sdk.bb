require genivi-dev-platform.bb

DESCRIPTION = "GENIVI Development Platform image that includes everything within \
genivi-dev-platform plus meta-toolchain, development headers and libraries \
to form a standalone SDK."

inherit populate_sdk populate_sdk_qt5

IMAGE_FEATURES_append = "\
	debug-tweaks         \
	dev-pkgs             \
	eclipse-debug        \
	qtcreator-debug      \
	tools-debug          \
	tools-profile        \
	tools-sdk            \
	tools-testapps       \
	"

IMAGE_INSTALL_append  = "\
	kernel-dev           \
	"
