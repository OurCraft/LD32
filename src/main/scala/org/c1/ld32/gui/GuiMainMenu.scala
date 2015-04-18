package org.c1.ld32.gui

import org.c1.ld32.Game
import org.c1.ld32.gui.action.ActionHandler
import org.lwjgl.input.Keyboard

class GuiMainMenu extends GuiScreen with ActionHandler {
  def init(): Unit = {
    elements.add(new GuiLabel("Main Menu", 10, 30))
    elements.add(new GuiKeypress(Keyboard.KEY_S, "Start Game", 10, 10))
  }

  def renderScreen(delta: Float): Unit = {

  }

  def onAction(id: Int): Unit = {
    Game displayGuiScreen new GuiIngame
  }
}
