FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

QT_MODULE_BRANCH = "5.4"

SRC_URI_append = "\
    file://0001-packaging-Import-from-Mer.patch \
    file://0002-Added-Tizen-spec-files.patch \
    file://0003-Build-qtwayland-only-when-with-wayland.patch \
    file://0004-Bump-package-version-to-5.2.1.patch \
    file://0005-Spec-Qt5WaylandClient-handled.patch \
    file://0006-spec-Added-mtdev-dependency.patch \
    file://0007-Fixed-build-when-with-x-and-with-wayland-is-set.patch \
    file://0008-Bump-package-version-to-5.2.2.patch \
    file://0009-Bump-package-version-to-5.2.90-alpha.patch \
    file://0010-Added-manifest-file-according-to-smack-3-domain-mode.patch \
    file://0011-packaging-updated-path-to-client-plugins.patch \
    file://0012-Bump-package-version-to-5.2.95-rc1.patch \
    file://0013-Bump-package-version-to-5.2.96-rc2.patch \
    file://0014-Bump-package-version-to-5.3.0.patch \
    file://0015-Bump-package-version-to-5.3.1.patch \
    file://0016-xdg-shell-Add-xdg-shell-protocol-file-version-1.4.0.patch \
    file://0017-xdg-shell-Add-minimize-feature-to-QWindow-using-wayl.patch \
    file://0018-packaging-enable-xdg-shell-at-runtime.patch \
    file://0019-xdg-shell-upgrade-to-support-current-version-weston-.patch \
    file://0020-Add-IVI-Shell-protocol-file-version-patch-v6.patch \
    file://0021-Implement-initial-IVI-Shell-support.patch \
    file://disable_xcomposite_egl_qt_wayland_client_buffer_integration.patch \
    file://0001-protocol-update-3rd-party-ivi-application-protocol.patch \
    file://0002-qwaylandwindow-add-support-for-IVI-Surface-ID-proper.patch \
    "

DEPENDS_append_koelsch = " libegl gles-user-module"
