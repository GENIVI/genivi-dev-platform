#! /bin/sh
dbus-send --print-reply --system --dest=org.genivi.NodeStateManager /org/genivi/NodeStateManager/LifecycleControl org.genivi.NodeStateManager.LifecycleControl.SetNodeState int32:6
/bin/sync
/bin/systemctl start --system poweroff.target
