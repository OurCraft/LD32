package org.oc.ld32.gui

import java.util

import org.lengine.render.RenderEngine

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

  def width: Float = RenderEngine.displayWidth
  def height: Float = RenderEngine.displayHeight
}
