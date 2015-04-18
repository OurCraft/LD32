package org.c1.ld32.gui

import java.util
import scala.collection.JavaConversions._

abstract class GuiScreen extends BaguetteGui {
  var elements = new util.ArrayList[BaguetteGui]()

  def init(): Unit

  def render(delta: Float): Unit = {
    renderScreen(delta)
    for (element <- elements) {
        element.render(delta)
    }
  }

  def renderScreen(delta: Float): Unit
}
