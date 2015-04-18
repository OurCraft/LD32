package org.c1.ld32.gui

import java.util

import org.c1.ld32.gui.action.ActionHandler

class GuiMainMenu extends GuiScreen with ActionHandler {
  def init(): Unit = {
    elements.add(new GuiLabel("Main Menu", 10, 90))

    val list = new util.ArrayList[String]()
    list.add("Start new game")
    list.add("Goto level")
    list.add("Quit game")
    elements.add(new GuiSelector(list, 10, 60))
  }

  def renderScreen(delta: Float): Unit = {

  }

  def onAction(source: Object, id: Int): Unit = {
    println("User clicked option " + id)
  }
}
