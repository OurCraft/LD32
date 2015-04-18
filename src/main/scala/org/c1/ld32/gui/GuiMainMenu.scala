package org.c1.ld32.gui

class GuiMainMenu extends GuiScreen {
  def init(): Unit = {
    elements.add(new GuiLabel("Main Menu", 10, 10))
    elements.add(new GuiButton("Button", 30, 30))
  }

  def renderScreen(delta: Float): Unit = {

  }
}
