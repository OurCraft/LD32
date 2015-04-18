package org.c1.ld32.gui

import org.c1.ld32.gui.action.ActionHandler

class GuiMainMenu extends GuiScreen with ActionHandler {
  def init(): Unit = {
    elements.add(new GuiLabel("Main Menu", 10, 10))
    elements.add(new GuiButton("Start Game", 10, 30))
  }

  def renderScreen(delta: Float): Unit = {

  }

  def onAction(id: Int): Unit = {
    println("Button " + id + " clicked!")
  }
}
