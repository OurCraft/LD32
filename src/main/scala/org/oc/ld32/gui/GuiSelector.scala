package org.oc.ld32.gui

import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.gui.action.ActionHandler

class GuiSelector(val options: java.util.ArrayList[String], x: Int, y: Int) extends BaguetteGui {
  var selectedIndex: Int = 0

  var shouldRender = true

  def render(delta: Float): Unit = {
    if (shouldRender) {
      for (i <- 0 until options.size()) {
        if (selectedIndex == i) fontRenderer.renderString("> " + options.get(i), x, y - i * 30, 0xFFFFFFFF)
        else fontRenderer.renderString(options.get(i), x, y - i * 30, 0xFFFFFFFF)
      }
    }
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if (keyCode == Keyboard.KEY_DOWN) {
      selectedIndex += 1
      if (selectedIndex == options.size) selectedIndex = 0
    }
    if (keyCode == Keyboard.KEY_UP) {
      selectedIndex -= 1
      if (selectedIndex < 0) selectedIndex = options.size - 1
    }
    if (keyCode == Keyboard.KEY_RETURN) {
      Game.currentGui match {
        case handler: ActionHandler => handler.onAction(this, selectedIndex)
        case _ =>
      }
    }
  }
}
