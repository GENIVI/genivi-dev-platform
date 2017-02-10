EXTRA_OECONF_append=" --with-nsmc=NodeStateMachineTest "

do_install_append() {
    install -m 755 ${B}/NodeStateMachineTest/NodeStateTest ${D}${bindir}/NodeStateTest
}
