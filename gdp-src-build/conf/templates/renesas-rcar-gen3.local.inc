# Include generic GDP local configuration
include local.inc

# Add R-Car Gfx and MMP packages to install
IMAGE_INSTALL_append_rcar-gen3 = "libdrm-kms kernel-module-gles gles-user-module libgbm-dev packagegroup-gstreamer1.0-plugins weston-bin kernel-module-pvrsrvkm"

MACHINE ?= "m3ulcb"

# This sets the SoC
# H3: r8a7795, M3: r8a7796
#SOC_FAMILY = "r8a7795"
SOC_FAMILY ?= "r8a7796"

# Linaro GCC
GCCVERSION = "linaro-5.2"

PACKAGE_CLASSES = "package_ipk"

# add the static lib to SDK toolchain
SDKIMAGE_FEATURES_append = " staticdev-pkgs"

# Disable optee in meta-linaro layer
BBMASK = "meta-linaro/meta-optee/recipes-security/optee"

# Enable Gfx Pkgs
MACHINE_FEATURES_append = " gsx"
MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

# for Wayland/Weston
PREFERRED_PROVIDER_virtual/libgles1 = ""
PREFERRED_PROVIDER_virtual/libgles2 = "gles-user-module"
PREFERRED_PROVIDER_virtual/egl = "gles-user-module"
PREFERRED_PROVIDER_virtual/libgl = ""
PREFERRED_PROVIDER_virtual/mesa = ""
PREFERRED_PROVIDER_libgbm = "libgbm"
PREFERRED_PROVIDER_libgbm-dev = "libgbm"
BBMASK .= "|mesa-gl"

# Build the Gstreamer version the Yocto BSP accelerates
PREFERRED_VERSION_gstreamer1.0 = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-plugins-bad = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-plugins-base = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-plugins-good = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-plugins-ugly = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-libav = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-rtsp-server = "1.6.3"
PREFERRED_VERSION_gstreamer1.0-omx = "1.2.0"

# Enable Multimedia features
MACHINE_FEATURES_append = " multimedia"

# for gstreamer omx plugins
LICENSE_FLAGS_WHITELIST = "commercial"
# for mmp test program
DISTRO_FEATURES_append = " mm-test"

# for weston v4l2 renderer
#DISTRO_FEATURES_append = " v4l2-renderer"

# OMX H263 decoder library for Linux (RTM0AC0000XV263D30SL40C)
#DISTRO_FEATURES_append = " h263dec_lib"

# OMX H264 decoder library for Linux (RTM0AC0000XV264D30SL40C)
DISTRO_FEATURES_append = " h264dec_lib"

# OMX H264 encoder library for Linux (RTM0AC0000XV264E30SL40C)
#DISTRO_FEATURES_append = " h264enc_lib"

# OMX H265 decoder library for Linux (RTM0AC0000XV265D30SL40C)
#DISTRO_FEATURES_append = " h265dec_lib"

# OMX MPEG2 decoder library for Linux (RTM0AC0000XVM2VD30SL40C)
#DISTRO_FEATURES_append = " mpeg2dec_lib"

# OMX Media Component MPEG4 Decoder Library for Linux (RTM0AC0000XVM4VD30SL40C)
#DISTRO_FEATURES_append = " mpeg4dec_lib"

# OMX Media Component VC-1 Decoder Library for Linux (RTM0AC0000XVVC1D30SL40C)
#DISTRO_FEATURES_append = " vc1dec_lib"

# OMX Media Component DivXD Decoder Library for Linux (RTM0AC0000XVDVXD30SL40C)
#DISTRO_FEATURES_append = " divxdec_lib"

# OMX Media Component RealVideo Decoder Library for Linux (RTM0AC0000XVRLVD30SL40C)
#DISTRO_FEATURES_append = " rvdec_lib"

# OMX Media Component ALAC Decoder Library for Linux (RTM0AC0000XAALAD30SL40C)
#DISTRO_FEATURES_append = " alacdec_lib"

# OMX Media Component FLAC Decoder Library for Linux (RTM0AC0000XAFLAD30SL40C)
#DISTRO_FEATURES_append = " flacdec_lib"

# OMX AAC-LC decoder library for Linux (RTM0AC0000XAAACD30SL40C),
# AAC-LC 2ch decoder middleware library for Linux (RTM0AC0000ADAACMZ1SL40C)
DISTRO_FEATURES_append = " aaclcdec_lib"
DISTRO_FEATURES_append = " aaclcdec_mdw"

# OMX aacPlus V2 decoder library for Linux (RTM0AC0000XAAAPD30SL40C),
# aacPlus V2 decoder middleware library for Linux (RTM0AC0000ADAAPMZ1SL40C)
#DISTRO_FEATURES_append = " aacpv2dec_lib"
#DISTRO_FEATURES_append = " aacpv2dec_mdw"

# OMX MP3 decoder library for Linux (RTM0AC0000XAMP3D30SL40C),
# MP3 decoder middleware library for Linux (RTM0AC0000ADMP3MZ1SL40C)
#DISTRO_FEATURES_append = " mp3dec_lib"
#DISTRO_FEATURES_append = " mp3dec_mdw"

# OMX WMA decoder library for Linux (RTM0AC0000XAWMAD30SL40C),
# WMA decoder middleware library for Linux (RTM0AC0000ADWMAMZ1SL40C)
#DISTRO_FEATURES_append = " wmadec_lib"
#DISTRO_FEATURES_append = " wmadec_mdw"

# OMX AAC-LC encoder library for Linux (RTM0AC0000XAAACE30SL40C)
# AAC-LC encoder middleware library for Linux (RTM0AC0000AEAACMZ1SL40C)
#DISTRO_FEATURES_append = " aaclcenc_lib"
#DISTRO_FEATURES_append = " aaclcenc_mdw"

# OMX Dolby(R) Digital decoder library for Linux (RTM0AC0000XADD5D30SL40C),
# Dolby(R) Digital decoder middleware library for Linux (RTM0AC0000ADDD5MZ1SL40C)
#DISTRO_FEATURES_append = " dddec_lib"
#DISTRO_FEATURES_append = " dddec_mdw"

# CMS Basic Color Management Middleware for Linux (RTM0AC0000JRCMBCV0SL40C)
#DISTRO_FEATURES_append = " cmsbcm"

# CMS CMM3 Backlight Control Middleware for Linux (RTM0AC0000JRCMBLC0SL40C)
#DISTRO_FEATURES_append = " cmsblc"

# CMS VSP2 Dynamic Gamma Correction Middleware for Linux (RTM0AC0000JRCMDGV0SL40C)
#DISTRO_FEATURES_append = " cmsdgc"

# ISDB-T DTV Software Package for Linux (RTM0RC0000TE020000SL40C)
#DISTRO_FEATURES_append = " dtv"

# DVD Core-Middleware for Linux (RTM0RC0000XDVDC301SL40C)
#DISTRO_FEATURES_append = " dvd"

# ADSP Driver for Linux (RCG3AHPDL4001ZDO)
# ADSP Interface for Linux (RCG3AHIFL4001ZDP)
# ADSP Framework (RCG3AHFWN0101ZDP)
#DISTRO_FEATURES_append = " adsp"

# AVB Software Package for Linux
DISTRO_FEATURES_append = " avb"

# Linux ICCOM driver (RCG3ZLIDL4001ZNO)
# Linux ICCOM library (RCG3ZLILL4001ZNO)
#DISTRO_FEATURES_append = " iccom"
