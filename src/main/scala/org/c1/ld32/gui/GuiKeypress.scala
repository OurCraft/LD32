package org.c1.ld32.gui

import org.c1.ld32.Game
import org.c1.ld32.gui.action.ActionHandler
import org.lwjgl.input.Keyboard

class GuiKeypress(key: Int, text: String, x: Int, y: Int) extends BaguetteGui {
  def render(delta: Float): Unit = {
    fontRenderer.renderString(Keyboard.getKeyName(key) + "> " + text, x, y, 0xffffff)
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if (keyCode == key) Game.currentGui match {
      case handler: ActionHandler => handler.onAction(key)
      case _ =>
    }
  }
}
