#!/bin/bash

#Automotive Message Broker
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
ambd >/dev/null 2>&1 --config  /etc/ambd/logreplayerconfig.in.json & 

#POI Server
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
poi-server -f /usr/share/navigation-service/poi-database-sample.db &

#enhanced-position-service
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
enhanced-position-service &

#Navit
mkdir -p ~/.navit
touch ~/.navit/bookmark.txt
echo "mg: 0xa6aef 0x5897d8" > ~/.navit/center.txt

export NAVIT_LIBDIR="/usr/lib/navit"
export NAVIT_PLATFORM="wayland"
export NAVIT_GRAPHICS="opengl"
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
LD_PRELOAD="/usr/lib/libEGL.so" navit /usr/share/navigation-service/navit_genivi_mapviewer.xml 2>/dev/null &

export NAVIT_LIBDIR="/usr/lib/navit"
export NAVIT_PLATFORM="wayland"
export NAVIT_GRAPHICS="opengl"
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
navit /usr/share/navigation-service/navit_genivi_navigationcore.xml 2>/dev/null &

#fuel stop advisor
sleep 3
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
fuel-stop-advisor &

#HMI
export DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/$UID/dbus/user_bus_socket
LD_PRELOAD="/usr/lib/libEGL.so" hmi-launcher /usr/bin/navigation-application/qml/application.qml 2> /tmp/test &

grep EGL /tmp/test
while [ $? -gt 0 ]
do
sleep 1 
grep EGL /tmp/test
done
rm /tmp/test
sleep 1 

PID=`pidof hmi-launcher`
HMI_LAYER=700
FSA_LAYER=600
HMI_SURFACE=`expr 8000 + $PID` 

LayerManagerControl set surface $HMI_SURFACE visibility 1

LayerManagerControl set layer $HMI_LAYER render order $HMI_SURFACE
LayerManagerControl set layer $HMI_LAYER visibility 1

LayerManagerControl set screen 0 render order $FSA_LAYER,$HMI_LAYER
