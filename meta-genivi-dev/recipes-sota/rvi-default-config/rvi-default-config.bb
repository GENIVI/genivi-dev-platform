SUMMARY = "Default Configuration files for rvi_lib"
HOMEPAGE = "https://github.com/GENIVI/rvi_lib"
BUGTRACKER = "https://github.com/GENIVI/rvi_lib/issues"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=50772b2cd18ba00801e433556c1924bc"
PR = "r0"

SECTION = "base"

SRC_URI = "\
    file://LICENSE \
    file://sota.toml \
    file://conf.json \
    file://priv/keys/insecure_device_key.pem \
    file://priv/certificates/insecure_device_cert.crt \
    file://priv/certificates/insecure_root_cert.crt \
    file://priv/credentials/insecure_credential.jwt \
    file://priv/credentials/insecure_credential.json \
"

S = "${WORKDIR}"

DIR = "${D}/${sysconfdir}/rvi"

do_install() {
    install -d ${DIR}
    install -d ${DIR}/priv
    install -d ${DIR}/priv/keys
    install -d ${DIR}/priv/certificates
    install -d ${DIR}/priv/credentials
    install -d ${D}/var/sota
    install sota.toml ${D}/${sysconfdir}/sota.toml
    install conf.json ${DIR}/conf.json
    install priv/keys/insecure_device_key.pem ${DIR}/priv/keys/insecure_device_key.pem
    install priv/certificates/insecure_device_cert.crt ${DIR}/priv/certificates/insecure_device_cert.crt
    install priv/certificates/insecure_root_cert.crt ${DIR}/priv/certificates/insecure_root_cert.crt
    install priv/credentials/insecure_credential.jwt ${DIR}/priv/credentials/insecure_credential.jwt
    install priv/credentials/insecure_credential.json ${DIR}/priv/credentials/insecure_credential.json
}
