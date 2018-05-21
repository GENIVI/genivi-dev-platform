from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.core.decorator.oeid import OETestID
from oeqa.runtime.decorator.package import OEHasPackage

class SanityCheckGDP(OERuntimeTestCase):

    # Copied from the poky/meta/lib/oeqa/runtime/cases/systemd.py test
    def systemctl(self, action='', target='', expected=0, verbose=False):
        command = 'systemctl %s %s' % (action, target)
        status, output = self.target.run(command)
        message = '\n'.join([command, output])
        if status != expected and verbose:
            cmd = 'systemctl status --full %s' % target
            message += self.target.run(cmd)[1]
        self.assertEqual(status, expected, message)
        return output

    @OETestID(2000)
    @OETestDepends(['ssh.SSHTest.test_ssh'])
    @OETestDepends(['systemd.SystemdServiceTests.test_systemd_disable_enable'])
    @OEHasPackage(["weston"])
    def test_weston_running(self):
        self.systemctl('is-active', 'weston.service', verbose=True)
