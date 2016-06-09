#!/bin/bash

pkill -9 "hmi-launcher"
pkill -9 -f "fuel-stop-advisor"
pkill -9 -f "navit"
pkill -9 -f "enhanced-position-service"
pkill -9 -f "poi-server"
pkill -9 -f "ambd"
reset
