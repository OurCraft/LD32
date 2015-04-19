package org.oc.ld32.input.gamepad

import org.oc.ld32.Game
import org.oc.ld32.input.keyboard.KeyControls

object Controls {

  var moveX = LogitechMapping.LEFT_X_AXIS
  var moveY = LogitechMapping.LEFT_Y_AXIS

  var lookX = LogitechMapping.RIGHT_X_AXIS
  var lookY = LogitechMapping.RIGHT_Y_AXIS

  var attack = LogitechMapping.RIGHT_TOP_TRIGGER
  var throwButton = LogitechMapping.LEFT_TOP_TRIGGER

  var confirm = LogitechMapping.BUTTON1

  def isConfirmPressed: Boolean = {
    Game.isKeyPressed(KeyControls.confirm) || Game.isButtonPressed(confirm)
  }

  def isConfirmKey(key: Int): Boolean = {
    key == KeyControls.confirm
  }

  def isConfirmButton(button: Int): Boolean = {
    button == confirm
  }
}
