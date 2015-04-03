#! /bin/sh
/bin/systemctl start --user gdp-hmi-controller.service
/bin/systemctl start --user gdp-hmi-background.service
/bin/systemctl start --user gdp-hmi-panel.service
/bin/systemctl start --user gdp-hmi-launcher2.service
