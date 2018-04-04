# Include generic GDP local configuration
include local.inc

# Add R-Car Gfx and MMP packages to install
IMAGE_INSTALL_append_rcar-gen3 = "\
    packagegroup-gstreamer1.0-plugins \
    packagegroup-wayland-community \
    packagegroup-graphics-renesas-proprietary \
    packagegroup-graphics-renesas-wayland \
"

MACHINE ?= "m3ulcb"

# This sets the SoC
# H3: r8a7795, M3: r8a7796
#SOC_FAMILY = "r8a7795"
SOC_FAMILY ?= "r8a7796"


PACKAGE_CLASSES = "package_rpm"

# add the static lib to SDK toolchain
SDKIMAGE_FEATURES_append = " staticdev-pkgs"

# Disable optee in meta-linaro layer
BBMASK = "meta-linaro/meta-optee/recipes-security/optee"

# Enable Gfx Pkgs
MACHINE_FEATURES_append = " gsx"
MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

# for Wayland/Weston
DISTRO_FEATURES_NATIVESDK_append = " wayland"
DISTRO_FEATURES_append = " pam"
PREFERRED_PROVIDER_virtual/libgles1 = ""
PREFERRED_PROVIDER_virtual/libgles2 = "gles-user-module"
PREFERRED_PROVIDER_virtual/egl = "libegl"
PREFERRED_PROVIDER_virtual/libgl = ""
PREFERRED_PROVIDER_virtual/mesa = ""
PREFERRED_PROVIDER_libgbm = "libgbm"
PREFERRED_PROVIDER_libgbm-dev = "libgbm"
BBMASK .= "|mesa-gl"

# Enable Multimedia features
MACHINE_FEATURES_append = " multimedia"

# for gstreamer omx plugins
LICENSE_FLAGS_WHITELIST = "commercial"
# for mmp test program
DISTRO_FEATURES_append = " mm-test"

# for weston v4l2 renderer
#DISTRO_FEATURES_append = " v4l2-renderer"

# OMX H263 decoder library for Linux (RTM0AC0000XV263D30SL41C)
#DISTRO_FEATURES_append = " h263dec_lib"

# OMX H264 decoder library for Linux (RTM0AC0000XV264D30SL41C)
DISTRO_FEATURES_append = " h264dec_lib"

# OMX H264 encoder library for Linux (RTM0AC0000XV264E30SL41C)
#DISTRO_FEATURES_append = " h264enc_lib"

# OMX H265 decoder library for Linux (RTM0AC0000XV265D30SL41C)
#DISTRO_FEATURES_append = " h265dec_lib"

# OMX MPEG2 decoder library for Linux (RTM0AC0000XVM2VD30SL41C)
#DISTRO_FEATURES_append = " mpeg2dec_lib"

# OMX Media Component MPEG4 Decoder Library for Linux (RTM0AC0000XVM4VD30SL41C)
#DISTRO_FEATURES_append = " mpeg4dec_lib"

# OMX Media Component VC-1 Decoder Library for Linux (RTM0AC0000XVVC1D30SL41C)
#DISTRO_FEATURES_append = " vc1dec_lib"

# OMX Media Component DivXD Decoder Library for Linux (RTM0AC0000XVDVXD30SL41C)
#DISTRO_FEATURES_append = " divxdec_lib"

# OMX Media Component RealVideo Decoder Library for Linux (RTM0AC0000XVRLVD30SL41C)
#DISTRO_FEATURES_append = " rvdec_lib"

# OMX Media Component ALAC Decoder Library for Linux (RTM0AC0000XAALAD30SL41C)
#DISTRO_FEATURES_append = " alacdec_lib"

# OMX Media Component FLAC Decoder Library for Linux (RTM0AC0000XAFLAD30SL41C)
#DISTRO_FEATURES_append = " flacdec_lib"

# OMX AAC-LC decoder library for Linux (RTM0AC0000XAAACD30SL41C),
# AAC-LC 2ch decoder middleware library for Linux (RTM0AC0000ADAACMZ1SL41C)
DISTRO_FEATURES_append = " aaclcdec_lib"
DISTRO_FEATURES_append = " aaclcdec_mdw"

# OMX aacPlus V2 decoder library for Linux (RTM0AC0000XAAAPD30SL41C),
# aacPlus V2 decoder middleware library for Linux (RTM0AC0000ADAAPMZ1SL41C)
#DISTRO_FEATURES_append = " aacpv2dec_lib"
#DISTRO_FEATURES_append = " aacpv2dec_mdw"

# OMX MP3 decoder library for Linux (RTM0AC0000XAMP3D30SL41C),
# MP3 decoder middleware library for Linux (RTM0AC0000ADMP3MZ1SL41C)
#DISTRO_FEATURES_append = " mp3dec_lib"
#DISTRO_FEATURES_append = " mp3dec_mdw"

# OMX WMA decoder library for Linux (RTM0AC0000XAWMAD30SL41C),
# WMA decoder middleware library for Linux (RTM0AC0000ADWMAMZ1SL41C)
#DISTRO_FEATURES_append = " wmadec_lib"
#DISTRO_FEATURES_append = " wmadec_mdw"

# OMX AAC-LC encoder library for Linux (RTM0AC0000XAAACE30SL41C)
# AAC-LC encoder middleware library for Linux (RTM0AC0000AEAACMZ1SL41C)
#DISTRO_FEATURES_append = " aaclcenc_lib"
#DISTRO_FEATURES_append = " aaclcenc_mdw"

# OMX Dolby(R) Digital decoder library for Linux (RTM0AC0000XADD5D30SL41C),
# Dolby(R) Digital decoder middleware library for Linux (RTM0AC0000ADDD5MZ1SL41C)
#DISTRO_FEATURES_append = " dddec_lib"
#DISTRO_FEATURES_append = " dddec_mdw"

# OMX Media Component VP8 Decoder Library for Linux (RTM0AC0000XVVP8D30SL41C)
#DISTRO_FEATURES_append = " vp8dec_lib"

# OMX Media Component VP8 Encoder Library for Linux (RTM0AC0000XVVP8E30SL41C)
#DISTRO_FEATURES_append = " vp8enc_lib"

# OMX Media Component VP9 Decoder Library for Linux (RTM0AC0000XVVP9D30SL41C)
#DISTRO_FEATURES_append = " vp9dec_lib"

# CMS Basic Color Management Middleware for Linux (RTM0AC0000JRCMBCV0SL41C)
#DISTRO_FEATURES_append = " cmsbcm"

# CMS CMM3 Backlight Control Middleware for Linux (RTM0AC0000JRCMBLC0SL41C)
#DISTRO_FEATURES_append = " cmsblc"

# CMS VSP2 Dynamic Gamma Correction Middleware for Linux (RTM0AC0000JRCMDGV0SL41C)
#DISTRO_FEATURES_append = " cmsdgc"

# ISDB-T DTV Software Package for Linux (RTM0RC0000TE020000SL41C)
#DISTRO_FEATURES_append = " dtv"

# DVD Core-Middleware for Linux (RTM0RC0000XDVDC301SL41C)
#DISTRO_FEATURES_append = " dvd"

# ADSP Driver for Linux (RCG3AHPDL4101ZDO)
# ADSP Interface for Linux (RCG3AHIFL4101ZDP)
# ADSP Framework (RCG3AHFWN0201ZDP)
#DISTRO_FEATURES_append = " adsp"

# AVB Software Package for Linux
DISTRO_FEATURES_append = " avb"

# Linux ICCOM driver (RCG3ZLIDL4101ZNO)
# Linux ICCOM library (RCG3ZLILL4101ZNO)
#DISTRO_FEATURES_append = " iccom"

# Evaluation packages
#DISTRO_FEATURES_append = " use_eva_pkg"

# Configuration for ivi-shell and ivi-extension
#DISTRO_FEATURES_append = " ivi-shell"

# Add Capacity Aware migration Strategy (CAS)
MACHINE_FEATURES_append = " cas"
