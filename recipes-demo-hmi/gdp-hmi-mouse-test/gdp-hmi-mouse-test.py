#!/usr/bin/python

# Copyright (c) 2015 GENIVI Alliance
# 
# Permission is hereby granted, free of charge, to any person obtaining a
# copy of this software and associated documentation files (the "Software"),
# to deal in the Software without restriction, including without limitation
# the rights to use, copy, modify, merge, publish, distribute, sublicense,
# and/or sell copies of the Software, and to permit persons to whom the
# Software is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included
# in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
# THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
# OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
# ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
# OTHER DEALINGS IN THE SOFTWARE.

import uinput
import time

def lerp_move(dev, x, y, period=0.01, steps=10):
  for i in range(steps):
    dev.emit(uinput.REL_X, x/steps, syn=False)
    dev.emit(uinput.REL_Y, y/steps)
    time.sleep(period)

def main():
  events = (uinput.REL_X,
            uinput.REL_Y,
            uinput.BTN_LEFT,
            uinput.BTN_RIGHT)
  width = 1024
  height = 768

  button_y = 700
  button_x = 900

  app_x = 512
  app_y = 500

  panel_x = 50
  panel_y = 720


  with uinput.Device(events) as device:
    time.sleep(0.01)
    device.emit(uinput.REL_X, -width, syn=False)
    device.emit(uinput.REL_Y, -height)

    time.sleep(0.01)

    while(True):
      # Reset cursor position
      lerp_move(device, -width, -height)
      time.sleep(0.01)

      # Move the wheel
      lerp_move(device, button_x, button_y)
      time.sleep(1)
      device.emit_click(uinput.BTN_LEFT)
      time.sleep(1)

      # Start an app
      lerp_move(device, app_x - button_x, app_y - button_y)
      time.sleep(1)
      device.emit_click(uinput.BTN_LEFT)
      time.sleep(2)

      # Close with the 'back' button
      lerp_move(device, panel_x - app_x, panel_y - app_y)
      time.sleep(1)
      device.emit_click(uinput.BTN_LEFT)
      time.sleep(1)

if __name__ == "__main__":
  main()

