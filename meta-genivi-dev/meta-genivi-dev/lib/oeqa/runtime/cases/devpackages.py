from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.core.decorator.oeid import OETestID

# Make sure that dev-pkgs are not installed unless explicitly enabled
# in IMAGE_FEATURES
class DevPackages(OERuntimeTestCase):

    @OETestID(2001)
    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_dev_packages(self):
        if ('dev-pkgs' in self.tc.td.get('IMAGE_FEATURES', '')) :
            self.skipTest('Found dev-pkgs in IMAGE_FEATURES')

        (status, output) = self.target.run('test -e /usr/include/stdint.h')
        msg = ('Development files found on target %s' %
              self.target.run('ls /usr/include/')[1])
        self.assertNotEqual(status, 0, msg=msg)
